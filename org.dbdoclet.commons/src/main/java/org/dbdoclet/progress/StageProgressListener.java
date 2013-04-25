package org.dbdoclet.progress;

public interface StageProgressListener extends ProgressVetoListener {

	public void setStageCount(int stageCount);
	public void nextStage();
	public void finished(String text);
}
