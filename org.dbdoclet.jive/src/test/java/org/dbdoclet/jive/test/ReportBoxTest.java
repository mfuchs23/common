package org.dbdoclet.jive.test;

import java.util.ArrayList;
import java.util.Locale;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ReportBox;
import org.dbdoclet.progress.DefaultReportItem;
import org.dbdoclet.progress.ReportItem;

public class ReportBoxTest {

    public static void main(String[] args) 
        throws Exception {

        JiveFactory.getInstance(Locale.getDefault());

        ArrayList<ReportItem> list = new ArrayList<ReportItem>();
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/01000_Success", DefaultReportItem.SUCCESS, "Erfolg"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/01000_Success", DefaultReportItem.SUCCESS, "Erfolg"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/01000_Success", DefaultReportItem.SUCCESS, "Erfolg"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/02000_Warning", DefaultReportItem.WARNING, "Warnung"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/03000_Error", DefaultReportItem.ERROR, "Fehler"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/04000_Fatal", DefaultReportItem.ERROR, "Nachricht", new Exception("Test Report Box")));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/02000_Warning", DefaultReportItem.WARNING, "Warnung"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/01000_Success", DefaultReportItem.SUCCESS, "Erfolg"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/02000_Warning", DefaultReportItem.WARNING, "Warnung"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/02000_Warning", DefaultReportItem.WARNING, "Warnung"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/03000_Error", DefaultReportItem.ERROR, "Fehler"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/03000_Error", DefaultReportItem.ERROR, "Fehler"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/03000_Error", DefaultReportItem.ERROR, "Fehler"));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/04000_Fatal", DefaultReportItem.ERROR, "Nachricht", new Exception("Test Report Box")));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/04000_Fatal", DefaultReportItem.ERROR, "Nachricht", new Exception("Test Report Box")));
        list.add(new DefaultReportItem("SNL/MODULES/Test/ReportDialog/LESSON/04000_Fatal", DefaultReportItem.ERROR, "Nachricht", new Exception("Test Report Box")));

        ReportBox.show("Fehlermeldung", 
                       "Lorem ipsum dolor sit amet, consectetuer adipiscing elit."
                       + " Lorem ipsum dolor sit amet, consectetuer adipiscing elit."
                       + " Lorem ipsum dolor sit amet, consectetuer adipiscing elit."
                       + " Lorem ipsum dolor sit amet, consectetuer adipiscing elit.",
                       list);
    }
}
