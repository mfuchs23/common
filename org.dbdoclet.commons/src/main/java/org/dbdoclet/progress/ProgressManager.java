package org.dbdoclet.progress;

import java.util.ArrayList;

public class ProgressManager {

	private final ArrayList<ProgressListener> listeners;

	public ProgressManager(ArrayList<ProgressListener> listeners) {

		this.listeners = listeners;

		if (listeners == null) {
			listeners = new ArrayList<ProgressListener>();
		}
	}

	public ProgressManager(ProgressListener listener) {

		this.listeners = new ArrayList<ProgressListener>();
		listeners.add(listener);
	}

	public void setProgressMaximum(int max) {

		for (ProgressListener listener : listeners) {
			listener.setProgressMaximum(max);
		}
	}

	public void setStageCount(int stageCount) {

		for (ProgressListener listener : listeners) {

			if (listener instanceof StageProgressListener) {
				((StageProgressListener) listener).setStageCount(stageCount);
			}
		}
	}


	public void nextStage() {

		for (ProgressListener listener : listeners) {

			if (listener instanceof StageProgressListener) {
				((StageProgressListener) listener).nextStage();
			}
		}
	}

	public boolean fireProgressEvent(ProgressEvent event) {

		boolean rc = true;

		if (listeners != null) {
			for (ProgressListener listener : listeners) {
				if (listener.progress(event) == false) {
					rc = false;
				}
			}
		}

		return rc;
	}

	public void finished(String text) {

		for (ProgressListener listener : listeners) {

			if (listener instanceof StageProgressListener) {
				((StageProgressListener) listener).finished(text);
			}
		}
	}
}
