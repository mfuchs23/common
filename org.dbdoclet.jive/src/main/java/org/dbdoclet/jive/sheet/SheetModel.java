package org.dbdoclet.jive.sheet;

import java.util.ArrayList;

public class SheetModel {

    // private static Log logger = LogFactory.getLog(SheetModel.class);

    private DocumentPart documentPart;
    private String header = "";

    private String footer = "";
    private int pageNumber = 1;
    
    public SheetModel(Sheet sheet) {
        documentPart = new DocumentPart(sheet);
    }

    public void addPart(Part part) {

        if (part == null) {
            return;
        }

        documentPart.appendChild(part);
    }

    public void clear() {
        documentPart.removeAll();
    }

    public SheetModel deepCopy(Sheet copySheet) {

        SheetModel copy = new SheetModel(copySheet);

        copy.setDocumentPart((DocumentPart) documentPart.deepCopy(copySheet));
        copy.setHeader(new String(header));
        copy.setFooter(new String(footer));
        copy.setPageNumber(pageNumber);
        
        return copy;
    }

    public DocumentPart getDocumentPart() {
        return documentPart;
    }

    public String getFooter() {
        return footer;
    }

    public String getHeader() {
        return header;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public Part getPart(int index) {

        ArrayList<Part> partList = documentPart.getChildren();

        if (partList == null || index < 0 || index >= partList.size()) {
            return null;
        }

        Part part = partList.get(index);
        return part;
    }

    public ArrayList<Part> getPartList() {

        ArrayList<Part> partList = documentPart.getChildren();
        return partList;
    }

    public ArrayList<Text> getTextList() {
        return getTextList(documentPart);
    }

    public ArrayList<Text> getTextList(Part part) {

        ArrayList<Text> textList = new ArrayList<Text>();
        
        if (part instanceof Text) {
            textList.add((Text) part);
        }
        
        for (Part child : part.getChildren()) { 
            textList.addAll(getTextList(child));
        }
        
        return textList;
    }

    public void insertPart(Part newPart, int index) {
        documentPart.insertChild(index, newPart);
    }

    public void insertPartBefore(Part part, Part referencePart) {

        int index = 0;
        boolean found = false;

        for (Part p : getPartList()) {

            if (p == referencePart) {
                found = true;
                break;
            }
            index++;
        }

        if (found == true) {
            documentPart.insertChild(index, part);
        }
    }

    public Part removePart(int index) {

        ArrayList<Part> partList = documentPart.getChildren();
        
        if (partList == null || index < 0 || index >= partList.size()) {
            return null;
        }

        return documentPart.removeChild(index);
    }

    public void removePart(Part part) {
        removePart(part, true);

    }

    public void removePart(Part part, boolean undoable) {

        documentPart.removeChild(part);
    }

    public void removePartById(String id) {

        Part foundPart = null;

        for (Part part : getPartList()) {

            if (part.getId() != null) {
                if (part != null && part.getId().equals(id)) {
                    foundPart = part;
                    break;
                }
            }
        }

        if (foundPart != null) {
            documentPart.removeChild(foundPart);
        }
    }

    public void setDocumentPart(DocumentPart documentPart) {
        this.documentPart = documentPart;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
