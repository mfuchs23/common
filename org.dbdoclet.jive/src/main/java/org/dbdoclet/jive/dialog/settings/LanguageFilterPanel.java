/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.model.LabelItem;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;

public class LanguageFilterPanel extends AbstractSettingsPanel 
    implements ActionListener {

    private static final long serialVersionUID = 1L;

    private TreeMap<String, Locale> selectedLocaleMap;
    private JList languageListBox;
    private JList selectedListBox;
    private ResourceBundle res;
    private JiveFactory wm;
    private Locale ctxLocale;
    
    public LanguageFilterPanel(Locale ctxLocale) {

        if (ctxLocale == null) {
            throw new IllegalArgumentException("The argument ctxLocale must not be null!");
        }

        this.ctxLocale = ctxLocale;

        wm = JiveFactory.getInstance();
        res = wm.getResourceBundle();
        
        setName("settings.panel.language-filter");
        setBorder(new EmptyBorder(4, 4, 4, 4));

        selectedLocaleMap = new TreeMap<String, Locale>();
        
        init();
    }
    
    public LanguageFilterPanel(Locale ctxLocale, File confFile) {
        
        this(ctxLocale);
        
        if (confFile == null) {
            throw new IllegalArgumentException("The argument confFile must not be null!");
        }
    }
    
    public String getNamespace() {
        return "language.";
    }
    
    @Override
    public Properties getProperties() {

        Properties properties = new Properties();

        String key;
        Locale locale;
        Iterator<String> iterator = selectedLocaleMap.keySet().iterator();
        
        int index = 0;
        
        while (iterator.hasNext()) {
        
            index++;
            
            key = iterator.next();
            locale = selectedLocaleMap.get(key);
            properties.setProperty("language.l" + index, locale.getLanguage());
        }

        return properties;
    }

    @Override
    public void setProperties(Properties properties) {

        if (properties == null) {
            throw new IllegalArgumentException("The argument properties must not be null!");
        }

        DefaultComboBoxModel model = (DefaultComboBoxModel) selectedListBox.getModel();
        int index = 1;
        
        String lang = properties.getProperty("language.l" + index++);
        
        while (lang != null) {
            
            Locale locale = new Locale(lang);

            if (selectedLocaleMap.get(locale.getDisplayLanguage(ctxLocale)) == null) {

                selectedLocaleMap.put(locale.getDisplayLanguage(ctxLocale), locale);
                model.addElement(new LabelItem(locale.getDisplayLanguage(ctxLocale), locale));
            }
            
            lang = properties.getProperty("language.l" + index++);
        }
    }

    public void actionPerformed(ActionEvent event) {

        String cmd = event.getActionCommand();
        
        if (cmd == null) {
            return;
        }

        if (cmd.equals("add")) {
            
            Locale locale;
            
            Object[] itemList = languageListBox.getSelectedValues();

            if (itemList != null) {

                for (int i = 0; i < itemList.length; i++) {
                
                    if (itemList[i] instanceof LabelItem) {

                        locale = (Locale) ((LabelItem) itemList[i]).getValue();
                        
                        if (selectedLocaleMap.get(locale.getDisplayLanguage(ctxLocale)) == null) {
                            selectedLocaleMap.put(locale.getDisplayLanguage(ctxLocale), locale);
                        }
                    }
                }
                
                DefaultComboBoxModel model = (DefaultComboBoxModel) selectedListBox.getModel();
                model.removeAllElements();
                    
                Iterator<String> iterator = selectedLocaleMap.keySet().iterator();
                        
                String label;
                    
                while (iterator.hasNext()) {
                    
                    label = iterator.next();
                    locale = selectedLocaleMap.get(label);
                    
                    model.addElement(new LabelItem(label, locale));
                }
            }

            languageListBox.clearSelection();
        }

        if (cmd.equals("remove")) {
            
            Locale locale;
            
            Object[] itemList = selectedListBox.getSelectedValues();

            if (itemList != null) {

                DefaultComboBoxModel model = (DefaultComboBoxModel) selectedListBox.getModel();

                for (int i = 0; i < itemList.length; i++) {

                    if (itemList[i] instanceof LabelItem) {

                        locale = (Locale) ((LabelItem) itemList[i]).getValue();
                
                        if (selectedLocaleMap.get(locale.getDisplayLanguage(ctxLocale)) != null) {
                            selectedLocaleMap.remove(locale.getDisplayLanguage(ctxLocale));
                        }
                    }
                    
                    model.removeElement(itemList[i]);
                }
            }
        }
    }

    private void init() {

        String[] isoLanguageList = Locale.getISOLanguages();
        Locale[] isoLocaleList = new Locale[isoLanguageList.length];

        for (int i = 0; i < isoLanguageList.length; i++) {
            isoLocaleList[i] = new Locale(isoLanguageList[i]);
        }

        DefaultComboBoxModel model = new DefaultComboBoxModel();

        TreeMap<String, Locale> localeMap = new TreeMap<String, Locale>();
        Locale locale;
        String text;
        String language;

        for (int i = 0; i < isoLocaleList.length; i++) {

            language = isoLocaleList[i].getLanguage();
            text = isoLocaleList[i].getDisplayLanguage(ctxLocale);
            locale = localeMap.get(text);
            
            if (locale == null) {
                localeMap.put(text, new Locale(language));
            }
        }

        String name;
        Iterator<String> iterator = localeMap.keySet().iterator();
        
        while (iterator.hasNext()) {
            
            name = iterator.next();
            locale = localeMap.get(name);
            model.addElement(new LabelItem(locale.getDisplayLanguage(ctxLocale), locale));
        }

        languageListBox = new JList();
        languageListBox.setModel(model);

        model = new DefaultComboBoxModel();

        iterator = selectedLocaleMap.keySet().iterator();
        
        while (iterator.hasNext()) {

            name = iterator.next();
            locale = selectedLocaleMap.get(name);
            model.addElement(new LabelItem(locale.getDisplayLanguage(ctxLocale), locale));
        }

        selectedListBox = new JList();
        selectedListBox.setModel(model);
        
        GridPanel selectButtonPanel = new GridPanel();

        JButton addButton = wm.createButton(null, ResourceServices.getString(res,"C_ADD"));
        addButton.addActionListener(this);
        addButton.setActionCommand("add");
        selectButtonPanel.addComponent(addButton, Anchor.NORTHWEST, Fill.HORIZONTAL);
        selectButtonPanel.incrRow();
        
        JButton removeButton = wm.createButton(null, ResourceServices.getString(res,"C_REMOVE"));
        removeButton.addActionListener(this);
        removeButton.setActionCommand("remove");
        selectButtonPanel.addComponent(removeButton, Anchor.NORTHWEST, Fill.HORIZONTAL);
        
        addComponent(new JScrollPane(languageListBox), Anchor.NORTHWEST, Fill.BOTH);
        addComponent(selectButtonPanel, Anchor.NORTHWEST, Fill.VERTICAL);
        addComponent(new JScrollPane(selectedListBox), Anchor.NORTHWEST, Fill.BOTH);
    }
}
