package org.dbdoclet.progress;

public class DefaultReportItem implements ReportItem {

    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;

    private int status = 0;
    private Throwable cause;
    private Object item;
    private String msg;

    public DefaultReportItem(Object item, int status) {

        this(item, status, null, null);
    }

    public DefaultReportItem(Object item, int status, String msg) {

        this(item, status, msg, null);
    }

    public DefaultReportItem(Object item, int status, String msg, Throwable cause) {

        if (item == null) {
            throw new IllegalArgumentException("The argument item must not be null!");
        }

        if (status != SUCCESS && status != ERROR && status != WARNING) {
            throw new IllegalArgumentException("The argument status is invalid!");
        }

        this.item = item;
        this.status = status;
        this.msg = msg;
        this.cause = cause;
    }

    public boolean isSuccess() {
        
        if (status == SUCCESS) {
            return true;
        }

        return false;
    }

    public boolean isError() {
        
        if (status == ERROR) {
            return true;
        }

        return false;
    }

    public boolean isWarning() {
        
        if (status == WARNING) {
            return true;
        }

        return false;
    }

    public Throwable getCause() {
        return cause;
    }

    public String getMessage() {
        return msg;
    }

    public Object getItem() {
        return item;
    }

    public String getSubject() {
        return toString();
    }

    @Override
    public String toString() {
        
        if (item != null) {
            return item.toString();
        }

        return "???";
    }
}
