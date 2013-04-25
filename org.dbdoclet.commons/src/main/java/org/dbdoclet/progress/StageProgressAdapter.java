package org.dbdoclet.progress;

public class StageProgressAdapter extends ProgressAdapter implements StageProgressListener {

	private int stageCount;

	public int getStageCount() {
		return stageCount;
	}

	@Override
	public void setStageCount(int stageCount) {
		this.stageCount = stageCount;
		
	}

	@Override
	public void nextStage() {
	}

	@Override
	public void finished(String text) {
	}

}
