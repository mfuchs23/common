package org.dbdoclet.progress;

public interface ProgressTimeListener extends ProgressVetoListener {

    /**
     * Setzt die Startzeit der Verarbeitung. Die Startzeit kann dazu verwendet
     * werden eine Schätzung der verbleibenden Dauer der Aktion zu berechnen und
     * anzuzeigen.
     */
    public void setProgressStartTime(long startTime);
}
