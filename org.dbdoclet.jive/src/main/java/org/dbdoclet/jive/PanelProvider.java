package org.dbdoclet.jive;

import javax.swing.JPanel;

/**
 * Schnittstelle für Klassen, die eine grafische Benutzeroberfläche zur Parametrisierung bereitstellen wollen.
 * 
 * @author Michael Fuchs
 */
public interface PanelProvider {

	/**
	 * Erzeugt ein JPanel zur Eingabe benötigter Parameter.
	 * 
	 * @return JPanel
	 */
	public JPanel getPanel();

}
