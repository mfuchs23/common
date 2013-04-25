package org.dbdoclet.progress;

public class ProgressAdapter implements ProgressVetoListener, ProgressTimeListener {

	private int progressMaximum = 0;
	private int progressCounter = 0;
	private long progressStartTime = System.currentTimeMillis();

	public int getProgressCounter() {
		return progressCounter;
	}

	public int getProgressMaximum() {
		return progressMaximum;
	}

	public long getProgressStartTime() {
		return progressStartTime;
	}

	public boolean isCanceled() {
		return false;
	}

	public boolean progress(ProgressEvent event) {
		progressCounter++;
		return true;
	}

	public void setProgressMaximum(int max) {
		this.progressMaximum = max;
	}

	public void setProgressStartTime(long startTime) {
		progressStartTime = startTime;
	}

	public boolean veto(ProgressEvent event) {
		return false;
	}

}
