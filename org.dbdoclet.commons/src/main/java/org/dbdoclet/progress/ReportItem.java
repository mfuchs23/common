package org.dbdoclet.progress;

public interface ReportItem {

    public boolean isSuccess();
    public boolean isError();
    public boolean isWarning();
    public Throwable getCause();
    public Object getItem();
    public String getSubject();
    public String getMessage();
}
