/* 
 * ### Copyright (C) 2006-2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.progress;

/**
 * Die Klasse <code>ProgressEvent</code> wird dazu verwendet um interessierte
 * Objekte über den Fortschritt einer Aktion zu unterrichten.
 */
public class ProgressEvent {

    public static final int STAGE_PREPARE = 1;
    public static final int STAGE_ACTION = 2;
    
    private boolean consider = true;
    private int max = -1;
    private int index = -1;
    private int stage = STAGE_ACTION;
    private String action = "";
    private String item = "";
    private Object userObject = null;

    /**
     * Erzeugt ein leeres Forschrittsobjekt.
     */
    public ProgressEvent() {

        this(-1, -1, "", "", true);
    }

    /**
     * Erzeugt ein Fortschrittsobjekt.
     *
     * @param action Die Aktionsbeschreibung.
     */
    public ProgressEvent(String action) {

        this(-1, -1, "", action, true);
    }

    /**
     * Erzeugt ein Fortschrittsobjekt.
     *
     * Der Parameter <code>consider</code> gibt an, ob das Ereignis in die
     * Berechnung des Fortschrittswertes eingehen soll oder nicht.
     * 
     * @param action Die Aktionsbeschreibung.
     * @param consider Schalter für die Gewichtung des Ereignisses.
     */
    public ProgressEvent(String action, boolean consider) {

        this(-1, -1, "", action, consider);
    }

    /**
     * Erzeugt ein Fortschrittsobjekt.
     *
     * @param item Beschreibung des verarbeiteten Objekts
     * @param action Aktionsbeschreibung.
     */
    public ProgressEvent(String item, String action) {

        this(-1, -1, item, action, true);
    }

    /**
     * Erzeugt ein Fortschrittsobjekt.
     *
     * @param index Index des Fortschrittswertes.
     * @param item Beschreibung des verarbeiteten Objekts.
     * @param action Aktionsbeschreibung.
     */
    public ProgressEvent(int index, String item, String action) {

        this(-1, index, item, action, true);
    }

    /**
     * Erzeugt ein Fortschrittsobjekt.
     *
     * @param max Maximalwert des Fortschrittswertes
     * @param index Aktueller Wert des Fortschrittswertes.
     */
     public ProgressEvent(int max, int index) {

        this(max, index, "", "", true);
    }

    /**
     * Erzeugt ein Fortschrittsobjekt.
     *
     * @param max Maximalwert des Fortschrittswertes
     * @param index Aktueller Wert des Fortschrittswertes.
     * @param item Beschreibung des verarbeiteten Objekts.
     * @param action Aktionsbeschreibung.
     */
    public ProgressEvent(int max, int index, String item, String action) {

        this(max, index, item, action, true);
    }

    /**
     * Erzeugt ein Fortschrittsobjekt.
     *
     * @param max Maximalwert des Fortschrittswertes
     * @param index Aktueller Wert des Fortschrittswertes.
     * @param item Beschreibung des verarbeiteten Objekts.
     * @param action Aktionsbeschreibung.
     */
    public ProgressEvent(int max, int index, String item, String action, boolean consider) {

        this.max = max;
        this.index = index;
        this.item = item;
        this.action = action;
        this.consider = consider;
    }

    public int getMax() {
        return max;
    }

    public int getIndex() {
        return index;
    }

    public void setItem(String item) {
        this.item = item;
    }
    
    public String getItem() {
        return item;
    }
    
    public void setConsider(boolean consider) {
        this.consider = consider;
    }
    
    public boolean getConsider() {
        return consider;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ProgressEvent setStage(int stage) {

        this.stage = stage;
        return this;
    }
    
    public int getStage() {
        return stage;
    }

    /**
     * Setzt ein benutzerdefiniertes Objekt.
     */
    public ProgressEvent setUserObject(Object userObject) {

        this.userObject = userObject;
        return this;
    }
    
    /**
     * Liefert das benutzerdefinierte Objekt.
     */
    public Object getUserObject() {
        return userObject;
    }
    
    public String toString() {
    	
    	StringBuilder buffer = new StringBuilder();
    	buffer.append(getAction());
    	
    	return buffer.toString();
    }
}
