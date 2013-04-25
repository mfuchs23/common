package org.dbdoclet.progress;

/**
 * <p>Die Klasse <code>ProgressListener</code> stellt eine Schnittstelle zur
 * Verfügung um Fortschrittsereignisse (<code>ProgressEvent</code>) zu
 * empfangen.</p>
 *
 * <p>Eine Klasse, die an Fortschrittsereignissen interessiert ist,
 * implementiert diese Schnittstelle. Eine Instanz dieser Klasse wird dann bei
 * einem Objekt mit Fortschrittsbearbeitung registiert. Wenn ein
 * Fortschrittsereignis erzeugt wird, wird dann die Methode
 * <code>progress</code> der <code>ProgressListener</code> Instanz aufgerufen.</p>
 */
public interface ProgressVetoListener extends ProgressListener {

    /**
     * Vor einem Verarbeitungsschritt kann der Sender nachfragen, ob der
     * Empfänger ein Veto für diesen Schritt einlegen will und eventuell die
     * Verarbeitung für diesen Schritt übergehen.
     *
     * <p>Sollen zum Beispiel Dateien in ein Zielverzeichnis kopiert werden,
     * kann der Sender für jede Datei nachfragen, ob der Kopiervorgang
     * ausgeführt werden soll. Der Empfänger kann dann prüfen, ob die Datei im
     * Zielverzeichnis bereits existiert und gegebenfalls den Benutzer fragen,
     * ob die Datei überschrieben werden darf. Falls der Benutzer dies für diese
     * und vielleicht auch alle weiteren Dateien verneint, wird ein Veto
     * eingelegt.
     */
    public boolean veto(ProgressEvent event);

    /**
     * Mit Hilfe der Methode <code>isCanceled</code> kann der ProgressListener
     * dem Aufrufer signalisieren, dass der Benutzer die Verarbeitung abbrechen
     * will, z.B. durch betätigen einer Schaltfläche "Abbrechen" einer
     * Fortschrittsanzeige.
     */
    public boolean isCanceled();

}
