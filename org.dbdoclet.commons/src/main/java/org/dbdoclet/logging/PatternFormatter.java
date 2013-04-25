package org.dbdoclet.logging;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.dbdoclet.service.StringServices;

public class PatternFormatter extends Formatter {

    private static final String LSEP = System.getProperty("line.separator");

    private String pattern = "[%p - %C.%M] %m%n";

    public PatternFormatter() {

        LogManager manager = LogManager.getLogManager();

        if (manager != null) {

            String str = manager.getProperty("org.dbdoclet.logging.PatternFormatter.pattern");
            if (str != null && str.length() > 0) {
                pattern = str;
            }
        }
    }

    @Override
    public String format(LogRecord record) {

        String msg = StringServices.replace(pattern, 
                                            "%m", 
                                            record.getMessage());

        String className = record.getSourceClassName();
        int index = className.lastIndexOf('.');

        if (index != -1 && index < className.length() - 1) {
            className = className.substring(className.lastIndexOf('.') + 1);
        }
        

        Level level = record.getLevel();
        String levelStr = StringServices.align(level.getLocalizedName(), 8, ' ');

        msg = StringServices.replace(msg, "%C", className);
        msg = StringServices.replace(msg, "%M", record.getSourceMethodName());
        msg = StringServices.replace(msg, "%p", levelStr);
        msg = StringServices.replace(msg, "%n", LSEP);

        if (level.equals(Level.SEVERE) || level.equals(Level.WARNING)) {
            msg = StringServices.replace(msg, "%P", "[" + levelStr + "]");
        } else {
            msg = StringServices.replace(msg, "%P ", "");
            msg = StringServices.replace(msg, "%P", "");
        }


        Throwable oops = record.getThrown();

        if (oops != null) {
            
            if (msg.endsWith(LSEP) == false) {
                msg += LSEP;
            }
            
            msg += "EXCEPTION: " + oops.getClass().getName();

            if (oops.getMessage() != null) {
                msg += " - " + oops.getMessage(); 
            }

            msg += LSEP;
        }

        return msg;
    }
}

