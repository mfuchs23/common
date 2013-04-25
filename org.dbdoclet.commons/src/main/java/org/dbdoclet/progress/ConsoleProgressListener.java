package org.dbdoclet.progress;

public class ConsoleProgressListener extends StageProgressAdapter {

    @Override
    public boolean progress(ProgressEvent event) {

        System.out.println(event.getAction().replaceAll("<[^<]*>", ""));
        return false;
    }
}
