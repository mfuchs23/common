package org.dbdoclet.progress;

public interface ProgressListener {

	/**
	 * Die Methode <code>progress</code> wird aufgerufen, wenn ein
	 * Fortschrittsereignis ausgelöst wurde.
	 *
	 * @param event Das Fortschrittsereignis (<code>ProgressEvent</code>).
	 *
	 * @return <code>boolean</code> Der Rückgabewert muß auf <code>false</code>
	 * gesetzt werden, wenn der Sender des Fortschrittsereignisses die
	 * Verarbeitung abbrechen soll.
	 */
	public boolean progress(ProgressEvent event);

    /**
     * Setzt die maximale Anzahl von protokollierten Verarbeitungsschritten, die
     * ein berechenbares Fortschrittsereignis auslösen.
     */
    public void setProgressMaximum(int max);

}