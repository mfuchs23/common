package org.dbdoclet.jive.dialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.Rowspan;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.jive.widget.ImagePreview;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;

public class DeleteImagesDialog extends AbstractDialog 
    implements ActionListener {

    private static final long serialVersionUID = 1L;

    private ArrayList<String> imageList;
    private JiveFactory wmap;
    private ResourceBundle res;
    private File dir;
    private int index;
    private boolean canceled = false;

    private JLabel dirLabel;
    private JLabel dirBox;
    private JLabel fileNameLabel;
    private JComboBox fileNameBox;
    private ImagePreview imagePreview;
    private JButton cancel;
    private JButton delete;
    private JButton deleteAll;
    private JButton next;

    public DeleteImagesDialog(Frame parent, File dir, ArrayList<String> imageList, String infoText) {

    	super(parent, "");
	
        if (dir == null) {
            throw new IllegalArgumentException("The argument dir must not be null!");
        }

        if (imageList == null) {
            throw new IllegalArgumentException("The argument imageList must not be null!");
        }

        try {

            String buffer;

            Collections.sort(imageList);
            
            wmap = JiveFactory.getInstance();
            res = wmap.getResourceBundle();

            this.dir = dir;
            this.imageList = imageList;

            setTitle(ResourceServices.getString(res,"C_DELETE_IMAGES"));

            index = 0;

            GridPanel panel = new GridPanel();
            imagePreview = new ImagePreview();

            if (infoText != null) {
                
                panel.addComponent(wmap.createHelpArea(null, panel, infoText), Anchor.NORTHWEST, Fill.HORIZONTAL);
                panel.incrRow();
            }

            dirLabel = wmap.createLabel(null, ResourceServices.getString(res,"C_DIRECTORY"));

            buffer = dir.getCanonicalPath();
            if (buffer.length() > 80) {
                buffer = "..." + buffer.substring(buffer.length() - 80);
            }

            dirBox = wmap.createLabel(null, buffer, Font.PLAIN);

            fileNameLabel = wmap.createLabel(null, ResourceServices.getString(res,"C_FILE_NAME"));

            if (imageList.size() > 0) {

                Object[] list = imageList.toArray();
                fileNameBox = wmap.createComboBox(new Identifier("images"), list);
                updatePreview();

            } else {

                fileNameBox = wmap.createComboBox(new Identifier("images"));
            }

            fileNameBox.setActionCommand("select");
            fileNameBox.addActionListener(this);

            delete = new JButton(ResourceServices.getString(res,"C_DELETE"));
            delete.setActionCommand("delete");
            delete.addActionListener(this);

            deleteAll = new JButton(ResourceServices.getString(res,"C_DELETE_ALL"));
            deleteAll.setActionCommand("delete-all");
            deleteAll.addActionListener(this);

            next = new JButton(ResourceServices.getString(res,"C_NEXT"));
            next.setActionCommand("next");
            next.addActionListener(this);

            cancel = new JButton(ResourceServices.getString(res,"C_CANCEL"));
            cancel.setActionCommand("cancel");
            cancel.addActionListener(this);

            panel.addComponent(dirLabel);
            panel.addComponent(dirBox);
            panel.incrRow();

            panel.addComponent(fileNameLabel);
            panel.addComponent(fileNameBox);
            panel.incrRow();

            GridPanel previewPanel = new GridPanel();
            previewPanel.addComponent(imagePreview, Anchor.CENTER, Fill.BOTH);
            panel.addComponent(previewPanel, Anchor.CENTER, Fill.BOTH);
            panel.incrRow();
            
            GridPanel buttonPanel = new GridPanel();
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonPanel.addComponent(next);
            buttonPanel.addComponent(delete);
            buttonPanel.addComponent(deleteAll);
            buttonPanel.addComponent(cancel);
            panel.addComponent(buttonPanel, Colspan.CS_2, Rowspan.RS_1, Anchor.CENTER, Fill.HORIZONTAL);

            if (imageList.size() == 0) {
                next.setEnabled(false);
                delete.setEnabled(false);
                deleteAll.setEnabled(false);
            }

            getContentPane().add(panel, BorderLayout.CENTER);
            pack();
            center();

        } catch (Throwable oops) {
            
            ExceptionBox ebox = new ExceptionBox(oops);
            ebox.setVisible(true);
            ebox.toFront();
        }
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void actionPerformed(ActionEvent actionEvent) {

        try {

            String cmd = actionEvent.getActionCommand();

            if (cmd.equals("cancel")) {

                setVisible(false);
                canceled = true;
                dispose();
                return;
            }

            if (cmd.equals("delete-all")) {

                for (int i = 0; i < imageList.size(); i++) {
                    FileServices.delete(getImageFile(i));
                }

                setVisible(false);
                canceled = false;
                dispose();

                return;
            }
                
            if (cmd.equals("delete")) {

                delete(index);
                return;
            }

            if (cmd.equals("select")) {

                index = fileNameBox.getSelectedIndex();

                if (index == -1 || index >= imageList.size()) {
                    return;
                }

                imagePreview.setImage(getImageFile(index));
                return;
            }

            if (cmd.equals("next")) {

                if (index >= imageList.size() - 1) {
                    index = 0;
                } else {
                    index++;
                }

                fileNameBox.setSelectedIndex(index);
                updatePreview();
            }

        } catch (Throwable oops) {

            ExceptionBox ebox = new ExceptionBox(oops);
            ebox.setVisible(true);
            ebox.toFront();

            index = 0;

            if (imageList.size() > 0) {
 
                imagePreview.setImage(getImageFile(index));

            } else {

                setVisible(false);
                dispose();
            }
        }
    }

    private File getImageFile(int index) {

        if (index < 0 || index >= imageList.size()) {
            return null;
        }
        
        String imageFileName = imageList.get(index);
        
        if (imageFileName == null || imageFileName.trim().length() == 0) {
            return null;
        }
        
        imageFileName = FileServices.appendFileName(dir, imageFileName);
        imageFileName = FileServices.normalizePath(imageFileName);
        File imageFile = new File(imageFileName);

        return imageFile;
    }

    private void updatePreview() {

        File file = getImageFile(index);

        if (file != null) {
            imagePreview.setImage(file);        
        }
    }

    private void delete(int index)
        throws Exception {
        
        if (index < 0 || index >= imageList.size()) {
            return;
        }

        int selected = fileNameBox.getSelectedIndex();

        if (index != selected) {

            fileNameBox.setSelectedIndex(index);
            imagePreview.setImage(getImageFile(index));
        }

        FileServices.delete(getImageFile(index));
        imageList.remove(index);
        
        if (imageList.size() == 0) {
            
            setVisible(false);
            dispose();
            return;
        }
        
        fileNameBox.removeItemAt(index);
        fileNameBox.setSelectedIndex(index);
        
        imagePreview.setImage(getImageFile(index));
    }        
}
