/* 
 * ### Copyright (C) 2004-2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.model;

public class LabelItem {

    private String label;
    private Object value;

    public LabelItem(String label, Object value) {

        if (label == null) {

            throw new IllegalArgumentException("Parameter label is null!");
        }

        if (value == null) {

            throw new IllegalArgumentException("Parameter value is null!");
        }

        this.label = label;
        this.value = value;
    }

    protected LabelItem() {

        label = "???";
        value = "???";
    }

    public String getLabel() {

        return label;
    }

    protected void setLabel(String label) {

        this.label = label;
    }

    public Object getValue() {

        return value;
    }

    protected void setValue(Object value) {

        if (value == null) {

            throw new IllegalArgumentException(
                "The argument value may not be null!");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {

            return false;
        }

        if (obj instanceof String) {

            String value = (String) obj;

            if (value.equals(this.value)) {
                return true;
            } else {
                return false;
            }
        }

        if (obj instanceof LabelItem) {

            LabelItem item = (LabelItem) obj;
            Object value = item.getValue();

            if (value.equals(this.value)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {

        int hashCode = value.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {

        return label;
    }
}
