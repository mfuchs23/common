package org.dbdoclet.jive.widget.imagemap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.service.ResourceServices;

public class ImageMap extends JPanel implements ImageObserver, MouseListener,
		MouseMotionListener {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Image img;
	private Map<Rectangle, Action> actionAreaMap;
	private Rectangle oldRectangle;
	private Rectangle activeRectangle;

	private static Log logger = LogFactory.getLog(ImageMap.class);

	public ImageMap(String resource) {
		super();
		logger.fatal("Entering ImageMap");
		if (resource == null) {
			throw new IllegalArgumentException(
					"Argument resource must not be null");
		}

		URL url = ResourceServices.getResourceAsUrl(resource);
		if (url != null) {
			try {
				this.img = ImageIO.read(url);
				if (img == null) {
					logger.fatal("Could not read image: " + resource
							+ " for some reason - the image is null");
				}
			} catch (IOException e) {
				logger.fatal("Could not read Image from url:" + resource, e);
			}
		} else {
			logger.fatal("Could not read from resource-url: " + resource
					+ " - resource is null");
		}
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public Map<Rectangle, Action> getActionAreaMap() {

		if (this.actionAreaMap == null) {
			this.actionAreaMap = new HashMap<Rectangle, Action>();
		}

		return this.actionAreaMap;
	}

	public void initActionAreaMap(ImageMapDefinition imgdef) {
		this.actionAreaMap = imgdef.getAreaMap();
	}

	/**
	 * Fügt eine neue Area mit dem verknüpften ActionHandler hinzu
	 * 
	 * @param rectangle
	 *            das Rechteck auf dem eine Action abgefangen werden soll
	 * @param actionHandler
	 *            die Action, welche durchgeführt wird. Ist dieser Wert null, so
	 *            wird an dieser Stelle eine Instanz des
	 *            {@link DummyActionHandler} verwendet
	 */
	public void addArea(Rectangle rectangle, Action actionHandler) {
		if (rectangle == null) {
			throw new IllegalArgumentException(
					"The parameter rectangle must not be null");
		}

		if (actionHandler == null) {
			actionHandler = new DummyActionHandler();
		}

		if (actionAreaMap == null) {
			actionAreaMap = new HashMap<Rectangle, Action>();
		}
		actionAreaMap.put(rectangle, actionHandler);
	}

	public Rectangle calcRectangle(int x, int y) {
		Rectangle retval = null;
		if (this.actionAreaMap != null) {
			for (Iterator<Rectangle> areaIterator = this.actionAreaMap.keySet()
					.iterator(); areaIterator.hasNext();) {
				Rectangle actrect = areaIterator.next();

				if (actrect.contains(new Point(x, y))) {
					retval = actrect;
					break;
				}
			}
		}
		return retval;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.img, 0, 0, this.img.getWidth(this),
				this.img.getHeight(this), Color.black, this);

		if (this.activeRectangle != null) {
			g.setColor(new Color(0, 0, 255, 100));
			g.fill3DRect(this.activeRectangle.x + 1,
					this.activeRectangle.y + 1,
					(int) this.activeRectangle.getWidth() - 2,
					(int) this.activeRectangle.getHeight() - 2, true);
		}

	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		boolean retval = super.imageUpdate(img, infoflags, x, y, width, height);
		this.repaint();
		return retval;
	}

	public void mouseClicked(MouseEvent e) {
		Rectangle rect = calcRectangle(e.getX(), e.getY());
		Action act = this.actionAreaMap.get(rect);
		if (act != null) {
			act.actionPerformed(null);
		}
		logger.fatal("Clicked: X=" + e.getX() + " - Y=" + e.getY());
	}

	public void mouseEntered(MouseEvent e) {
		;
	}

	public void mouseExited(MouseEvent e) {
		this.activeRectangle = null;
		this.oldRectangle = null;
		this.repaint();
	}

	public void mousePressed(MouseEvent e) {
		;

	}

	public void mouseReleased(MouseEvent e) {
		;

	}

	public void mouseDragged(MouseEvent e) {
		;

	}

	public void mouseMoved(MouseEvent e) {
		this.oldRectangle = this.activeRectangle;
		this.activeRectangle = calcRectangle(e.getX(), e.getY());
		if (this.activeRectangle != null) {
			this.repaint();
		}

		// der mouseout auf das rectangle
		if (this.activeRectangle == null && this.oldRectangle != null) {
			this.repaint();
		}
	}

}
