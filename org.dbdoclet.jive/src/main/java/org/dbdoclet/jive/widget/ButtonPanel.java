package org.dbdoclet.jive.widget;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.service.ResourceServices;

public class ButtonPanel extends GridPanel {

    private static final long serialVersionUID = 1L;

    public static final int OK         = 0x0001;
    public static final int CANCEL     = 0x0002;
    public static final int CLOSE      = 0x0004;
    public static final int HELP       = 0x0008;
    public static final int SAVE       = 0x0010;
    public static final int EXPORT     = 0x0020;
    public static final int IMPORT     = 0x0040;
    public static final int PRINT      = 0x0080;
    public static final int CSV_EXPORT = 0x0100;
    public static final int YES        = 0x0200;
    public static final int NO         = 0x0400;
    
    private int flags = OK | CANCEL;
    private JiveFactory wm;
    private ResourceBundle res;
    private JButton cancelButton;
    private JButton okButton;
    private JButton saveButton;
    private JButton exportButton;
    private JButton importButton;
    private JButton printButton;
    private JButton csvExportButton;
    private JButton yesButton;
    private JButton noButton;
    private ActionListener listener;

    public ButtonPanel(int flags, ActionListener listener) {

        this.flags = flags;
        this.listener = listener;

        wm = JiveFactory.getInstance();
        res = wm.getResourceBundle();

        init();
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getYesButton() {
        return yesButton;
    }

    public JButton getNoButton() {
        return noButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public JButton getCsvExportButton() {
        return csvExportButton;
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JButton getPrintButton() {
        return printButton;
    }

    private void init() {

        if ((flags & OK) == OK) {

            okButton = wm.createButton(new Identifier("button-panel.ok"), ResourceServices.getString(res,"C_OK"));
            okButton.setActionCommand("ok");
            okButton.addActionListener(listener);
            okButton.setMnemonic(KeyEvent.VK_O);

            addComponent(okButton);
        }

        if ((flags & CANCEL) == CANCEL) {

            cancelButton = wm.createButton(new Identifier("button-panel.cancel"), ResourceServices.getString(res,"C_CANCEL"));
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(listener);
            cancelButton.setMnemonic(KeyEvent.VK_C);

            addComponent(cancelButton);
        }

        if ((flags & YES) == YES) {

            yesButton = wm.createButton(new Identifier("button-panel.yes"), ResourceServices.getString(res,"C_YES"));
            yesButton.setActionCommand("yes");
            yesButton.addActionListener(listener);
            yesButton.setMnemonic(KeyEvent.VK_Y);

            addComponent(yesButton);
        }

        if ((flags & NO) == NO) {

            noButton = wm.createButton(new Identifier("button-panel.no"), ResourceServices.getString(res,"C_NO"));
            noButton.setActionCommand("no");
            noButton.addActionListener(listener);
            noButton.setMnemonic(KeyEvent.VK_N);

            addComponent(noButton);
        }

        if ((flags & SAVE) == SAVE) {

            saveButton = wm.createButton(new Identifier("button-panel.save"), ResourceServices.getString(res,"C_SAVE"));
            saveButton.setActionCommand("save");
            saveButton.addActionListener(listener);
            saveButton.setMnemonic(KeyEvent.VK_S);

            addComponent(saveButton);
        }

        if ((flags & EXPORT) == EXPORT) {

            exportButton = wm.createButton(new Identifier("button-panel.export"), ResourceServices.getString(res,"C_EXPORT"));
            exportButton.setActionCommand("export");
            exportButton.addActionListener(listener);
            exportButton.setMnemonic(KeyEvent.VK_E);

            addComponent(exportButton);
        }

        if ((flags & IMPORT) == IMPORT) {

            importButton = wm.createButton(new Identifier("button-panel.import"), ResourceServices.getString(res,"C_IMPORT"));
            importButton.setActionCommand("import");
            importButton.addActionListener(listener);
            importButton.setMnemonic(KeyEvent.VK_I);

            addComponent(importButton);
        }

        if ((flags & CSV_EXPORT) == CSV_EXPORT) {

            csvExportButton = wm.createButton(new Identifier("button-panel.csv-export"), ResourceServices.getString(res,"C_CSV_EXPORT"));
            csvExportButton.setActionCommand("csv-export");
            csvExportButton.addActionListener(listener);
            csvExportButton.setMnemonic(KeyEvent.VK_C);

            addComponent(csvExportButton);
        }

        if ((flags & PRINT) == PRINT) {

            printButton = wm.createButton(new Identifier("button-panel.print"), ResourceServices.getString(res,"C_PRINT"));
            printButton.setActionCommand("print");
            printButton.addActionListener(listener);
            printButton.setMnemonic(KeyEvent.VK_P);

            addComponent(printButton);
        }
    }
}

