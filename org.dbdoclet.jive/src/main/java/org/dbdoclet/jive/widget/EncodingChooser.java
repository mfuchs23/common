package org.dbdoclet.jive.widget;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class EncodingChooser extends JComboBox<String> {

    private static final long serialVersionUID = 1L;

    public EncodingChooser() {

        SortedMap<String, Charset> map = Charset.availableCharsets();
        Iterator<String> iterator = map.keySet().iterator();

        String[] encodings = new String[map.size()];

        int index = 0;
        while (iterator.hasNext()) {
            encodings[index++] = iterator.next();
        }

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(encodings);
        setModel(model);
        setSelectedItem("UTF-8");
    }

    public String getEncoding() {

        return (String) getSelectedItem();
    }

    public void setEncoding(String encoding) {

        if (encoding == null) {
            return;
        }
 
        setSelectedItem(encoding);
    }
}