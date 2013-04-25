package org.dbdoclet.jive.widget.imagemap;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.Action;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.xiphias.XmlServices;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Liest eine ImageMap-Definition im HTML-Format ein und generiert eine Reihe
 * von {@link Rectangle}-Instanzen mit den dazugehörigen Action-Klassen. Die
 * Klasse ist derzeit nur mit dem mit Gimp erzeugten Format getestet. Es werden
 * desweiteren auch nur rechteckige Map-Areas zugelassen, Sollten andere Maps
 * mit eingebunden sein, so werden diese bei der HashMap-Erzeugung ignoriert und
 * eine entsprechende Warnmeldung ausgegeben (LogLevel = fatal). <br>
 * Eine {@link ImageMapDefinition} wird mit einem String erzeugt, der auf eine
 * Resource verweist:
 * 
 * <pre>
 * Bespiel:
 *      ImageMapDefinition id = new ImageMapDefinition(&quot;images/stylechooser.png.map&quot;);
 * </pre>
 * 
 * Durch die Instanziierung wird versucht die referenzierte Datei zu laden und
 * eine HashMap zu generieren welche direkt von der Klasse {@link ImageMap}
 * verwendet werden kann
 * 
 * @author alexander.braun@unico-group.com
 * 
 */
public class ImageMapDefinition {
	private static Log logger = LogFactory.getLog(ImageMapDefinition.class);

	private String imageMapContent;
	private HashMap<Rectangle, Action> areaMap;

	/**
	 * Generiert die areaMap, welche das Mapping zwischen Actions und Rectancles
	 * beinhaltet.
	 * 
	 * @param mapFileResource
	 * @throws ImageMapException
	 */
	public ImageMapDefinition(String mapFileResource) throws ImageMapException {
		if (mapFileResource == null) {
			throw new IllegalArgumentException(
					"Argument mapFileResource must not be null");
		}

		imageMapContent = "<mymap>";
		String line = "";
		try {
			InputStream io = ResourceServices
					.getResourceAsStream(mapFileResource);
			BufferedReader br = new BufferedReader(new InputStreamReader(io));

			while (line != null) {
				imageMapContent += "\n" + line;
				line = br.readLine();
			}
		} catch (IOException e) {
			throw new ImageMapException(
					"Got IOException while trying to parse the resource "
							+ mapFileResource, e);
		}
		imageMapContent += "\n</mymap>";
		readAreas();
	}

	/**
	 * Liest die Koordinaten und die als alt abgespeicherten Klassenpfade ein.
	 * Es werden nur shape="rect" areas akzeptiert
	 * 
	 * @throws ImageMapException
	 *             in einem der folgenden Fälle:
	 *             <ul>
	 *             <li>Temporäre XML-Datei konnte nicht erstellt werden
	 *             <li>Aus Temporärer XML-Datei konnte nicht gelesen werden
	 *             <li>Eine Sax- oder Parser Exception trat auf
	 *             </ul>
	 */
	protected void readAreas() throws ImageMapException {
		if (areaMap == null) {
			areaMap = new HashMap<Rectangle, Action>();
		}
		File tmpFile = new File("./imgm");
		try {
			FileServices.writeFromString(tmpFile, imageMapContent);
		} catch (IOException e1) {
			throw new ImageMapException("could not write temporary file: "
					+ tmpFile.getAbsolutePath(), e1);
		}

		try {
			Document doc;
			try {
				doc = XmlServices.parse(tmpFile);
			} catch (IOException e) {
				throw new ImageMapException("Could not read from "
						+ tmpFile.getAbsolutePath() + " to parse XML-file", e);
			}

			NodeList nl = doc.getElementsByTagName("area");

			for (int i = 0; i < nl.getLength(); i++) {
				Node actnode = nl.item(i);
				NamedNodeMap atts = actnode.getAttributes();
				String alt = null;
				String coords = null;

				// attribute durch lesen
				for (int j = 0; j < atts.getLength(); j++) {
					String nm = atts.item(j).getNodeName();
					String val = atts.item(j).getNodeValue();
					if (nm != null) {
						if (nm.toLowerCase().equals("alt")) {
							alt = val;
						} else if (nm.toLowerCase().equals("coords")) {
							coords = val;
						}
					}
				}

				// alt und coords have to be defined
				if (alt == null || coords == null) {
					logger.error("Attribute alt and attribute coords has to be defined! - Change your"
							+ "imagemap-file - ignoring map-area: "
							+ alt
							+ " with coords: " + coords);
				} else {
					Rectangle rect = null;

					try {
						rect = getRectangle(coords);
					} catch (ImageMapException e) {
						logger.fatal(e.getMessage() + alt);
					}

					// now reading the class and trying to create the
					// corresponding action-class
					Action action = getAction(alt);

					if (action != null && rect != null) {
						this.areaMap.put(rect, action);
					}
				}
			}

		} catch (SAXException e) {
			logger.fatal("Error while parsing imagemap: " + this.getContent(),
					e);
			throw new ImageMapException("Error while parsing imagemap:", e);
		} catch (ParserConfigurationException e) {
			logger.fatal("Error while parsing imagemap: " + this.getContent(),
					e);
			throw new ImageMapException("Error while parsing imagemap:", e);
		}
	}

	/**
	 * Versucht eine {@link Action} aus dem übergebenen Parameter zu erstellen
	 * 
	 * @param alt
	 *            der voll qualifizierte Klassenname
	 * @return null, wenn keine Action erzeugt werden konnte
	 */
	private Action getAction(String alt) {
		Class<?> actClass = null;
		Action retval = null;
		try {
			actClass = Class.forName(alt);
		} catch (ClassNotFoundException e) {
			logger.fatal("Could not find class: " + alt
					+ " no such action supported");
		}

		if (actClass != null) {
			try {
				retval = (Action) actClass.newInstance();
			} catch (InstantiationException e) {
				logger.fatal("Could not instantiate "
						+ alt
						+ " - class is abstract, interface, aray-class, a primitive type or void"
						+ " or no default constructor present");
			} catch (IllegalAccessException e) {
				logger.fatal("could not access constructor of classobject for "
						+ alt, e);
			} catch (ClassCastException e) {
				logger.fatal("could not generate action from " + alt
						+ " - perhaps not an Action class?", e);
			}
		}

		return retval;
	}

	/**
	 * Berechnet aus einem String mit Koordinaten ein {@link Rectangle}
	 * 
	 * @param coords
	 *            wird dargestellt in der Form "x1,y1,x2,y2"
	 * @return das berechnete {@link Rectangle} oder null, wenn kein
	 *         {@link Rectangle} erzeugt werden konnte
	 * @throws ImageMapException
	 *             wenn nicht exakt 4 Koordinaten aus dem coords-Parameter
	 *             extrahiert werden konnten.
	 */
	private Rectangle getRectangle(String coords) throws ImageMapException {
		StringTokenizer tok = new StringTokenizer(coords, ",");

		// only rect-regions are supported
		if (tok.countTokens() != 4) {
			throw new ImageMapException(
					"Wrong number of coordinates is defined for the area: - will ignore area");
		} else {

			// now reading the coordinates and associating them with the
			// action-name
			String token = tok.nextToken();
			int x, y, x2, y2;

			x = Integer.parseInt(token);

			token = tok.nextToken();
			y = Integer.parseInt(token);

			token = tok.nextToken();
			x2 = Integer.parseInt(token);

			token = tok.nextToken();
			y2 = Integer.parseInt(token);

			int width = x2 - x;
			int height = y2 - y;

			return new Rectangle(x, y, width, height);
		}
	}

	/**
	 * @return das gelesene ImageMap-File
	 */
	public String getContent() {
		return imageMapContent;
	}

	/**
	 * @return Area-Action Map, wie sie für {@link ImageMap#getActionAreaMap()}
	 *         notwendig ist
	 */
	public HashMap<Rectangle, Action> getAreaMap() {
		return areaMap;
	}

}
