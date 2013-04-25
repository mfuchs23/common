package org.dbdoclet.jive.test;

import org.dbdoclet.jive.sheet.PaperFormat;
import org.dbdoclet.jive.sheet.PaperFormatDialog;
import org.dbdoclet.jive.sheet.PaperSize;
import org.dbdoclet.unit.LengthUnit;
import org.junit.Test;

public class PaperFormatTests {

    @Test
    public void test_1() {

        PaperFormat pf = PaperFormat.valueOf(new PaperSize(210, 297, LengthUnit.MILLIMETER));
        pf.setOrientation(PaperFormat.Orientation.LANDSCAPE);
        
        PaperFormatDialog dlg = new PaperFormatDialog(null, "Seiteneinstellungen");
        dlg.setPaperFormat(pf);
        dlg.createGui();
        dlg.setVisible(true);

        if (dlg.isCanceled() == false) {
            System.out.println(dlg.getPaperFormat());
        }
    }
}
