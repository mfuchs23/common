/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.widget;

import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.dbdoclet.jive.model.LabelItem;

public class LanguageListBox extends JComboBox {

    private static final long serialVersionUID = 1L;
    private static Locale[] defLocaleList = null;
    private Locale ctxLocale;
    
    public LanguageListBox(Locale ctxLocale) {

        Locale[] localeList = defLocaleList;
        
        if (localeList == null) {

            String[] isoLanguageList = Locale.getISOLanguages();
            localeList = new Locale[isoLanguageList.length];
        
            for (int i = 0; i < isoLanguageList.length; i++) {
                localeList[i] = new Locale(isoLanguageList[i]);
            }
        }
        
        init(ctxLocale, localeList);
    }
    
    public LanguageListBox(Locale ctxLocale, Locale[] localeList) {
        
        init(ctxLocale, localeList);
    }
    
    public static void setDefaultLocaleList(Locale[] defLocaleList) {
        
        LanguageListBox.defLocaleList = defLocaleList;
    }
    
    public void setSelectedLocale(Locale locale) {

        if (locale == null) {
            return;
        }

        Locale languageLocale = new Locale(locale.getLanguage());
        setSelectedItem(new LabelItem(locale.getDisplayLanguage(ctxLocale), languageLocale));
    }

    public Locale getSelectedLocale() {

        Object obj = getSelectedItem();

        if (obj == null) {
            return null;
        }

        if (obj instanceof LabelItem == false) {
            return null;
        }

        LabelItem item = (LabelItem) obj;

        obj = item.getValue();

        if (obj instanceof Locale) {
            return (Locale) obj;
        }
        
        return null;
    }

    public void init(Locale ctxLocale, Locale[] localeList) {

        if (ctxLocale == null) {
            ctxLocale = Locale.getDefault();
        }

        if (localeList == null) {
            throw new IllegalArgumentException("The argument localeList must not be null!");
        }

        this.ctxLocale = ctxLocale;

        DefaultComboBoxModel model = new DefaultComboBoxModel();

        TreeMap<String, Locale> localeMap = new TreeMap<String, Locale>();
        Locale locale;
        String text;
        String language;

        for (int i = 0; i < localeList.length; i++) {

            language = localeList[i].getLanguage();
            text = localeList[i].getDisplayLanguage(ctxLocale);
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

        setModel(model);

        setSelectedItem(new LabelItem(ctxLocale.getDisplayLanguage(ctxLocale), ctxLocale));
    }

}
