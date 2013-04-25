package org.dbdoclet.jive.widget;

import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.service.ResourceServices;

public class StatusBar extends GridPanel {

    private static final long serialVersionUID = 1L;
    private int pageIndex = 1;
    private int numberOfPages = 1;
    private JiveFactory jive;
    private ResourceBundle res;
    private JLabel pageInfo;

    public StatusBar() {
        super();

        jive = JiveFactory.getInstance();
        res = jive.getResourceBundle();
    }

    public void createGui() {

        // setBorder(BorderFactory.createLineBorder(Color.red));
        addPageInfo();
        addHorizontalGlue();
    }
    public int getNumberOfPages() {
        return numberOfPages;
    }
    public int getPageIndex() {
        return pageIndex;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    private void addPageInfo() {

        pageInfo = new JLabel(createPageInfoText());
        pageInfo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        addComponent(pageInfo, Anchor.WEST);
    }

    private String createPageInfoText() {
        return " " + ResourceServices.getString(res, "page") + " " + pageIndex + "/" + numberOfPages
                + " ";
    }

    public void updatePageInfo(int pageIndex, int numberOfPages) {
       
        this.pageIndex = pageIndex;
        this.numberOfPages = numberOfPages;
        
        pageInfo.setText(createPageInfoText());
    }
}
