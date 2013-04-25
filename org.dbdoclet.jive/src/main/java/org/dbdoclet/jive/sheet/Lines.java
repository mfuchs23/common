package org.dbdoclet.jive.sheet;

import java.util.ArrayList;

public class Lines {
    
    ArrayList<Line> lineList;
    
    public Lines() {
        lineList = new ArrayList<Line>();
    }
    
    public void add(Line line) {
        
        if (line != null && lineList.contains(line) == false) {
            lineList.add(line);
        }
    }

    public Line getFirstLine() {

        if (lineList != null && lineList.size() > 0) {
            return lineList.get(0);
        }
        
        return null;
    }

    public Line getLine(int index) {

        if (lineList != null && lineList.size() > index) {
            return lineList.get(index);
        }
        
        return null;
    }

    public ArrayList<Line> getList() {
        return lineList;
    }

    public void clear() {
        lineList.clear();
    }

    public int size() {
        return lineList.size();
    }

    public Line getLastLine() {

        if (lineList.size() > 0) {
            return lineList.get(lineList.size() -1);
        }
        
        return null;
    }
}
