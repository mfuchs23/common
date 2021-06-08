package org.dbdoclet.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Die Klasse ArrayServices bietet Methoden für die Bearbeitung von Arrays.
 *
 */
public class ArrayServices {

    /**
     * Die Methode <code>concat</code> verknüpft 2 Arrays des Typs
     * String[] zu einem neuen String[] Array.
     * 
     * @see java.lang.System#arraycopy
     */
    public static String[] concat(String[] array1, String[] array2) {

        if (array1 == null) {
            throw new IllegalArgumentException("The argument array1 must not be null!");
        }

        if (array2 == null) {
            throw new IllegalArgumentException("The argument array2 must not be null!");
        }

        String[] array = new String[array1.length + array2.length];

        System.arraycopy(array1, 0, array, 0, array1.length);
        System.arraycopy(array2, 0, array, array1.length, array2.length);

        return array;
    }

    public static File[] concat(File[] array1, File[] array2) {

        if (array1 == null) {
            throw new IllegalArgumentException("The argument array1 must not be null!");
        }

        if (array2 == null) {
            throw new IllegalArgumentException("The argument array2 must not be null!");
        }

        File[] array = new File[array1.length + array2.length];

        System.arraycopy(array1, 0, array, 0, array1.length);
        System.arraycopy(array2, 0, array, array1.length, array2.length);

        return array;
    }

    /**
     * Die Methode concat verknüpft 2 Arrays des Typs Object[] zu einem neuen Array.
     */
    public static Object[] concat(Object[] array1, Object[] array2) {

        if (array1 == null) {
            throw new IllegalArgumentException("The argument array1 must not be null!");
        }

        if (array2 == null) {
            throw new IllegalArgumentException("The argument array2 must not be null!");
        }

        Object[] array = new Object[array1.length + array2.length];

        System.arraycopy(array1, 0, array, 0, array1.length);
        System.arraycopy(array2, 0, array, array1.length, array2.length);

        return array;
    }

    public static String[] listToSortedStringArray(ArrayList<String> list) {

        if (list == null) {
            throw new IllegalArgumentException("The argument list must not be null!");
        }

        Object[] objArray = list.toArray();
        Arrays.sort(objArray);

        String[] strArray = new String[objArray.length];

        for (int i = 0; i < objArray.length; i++) {

            if (objArray[i] == null) {
                strArray[i] = null;
                continue;
            }

            if (objArray[i] instanceof String) {
                strArray[i] = (String) objArray[i];
            } else {
                strArray[i] = objArray.toString();
            }
        }

        return strArray;
    }

    public static String[] listToStringArray(ArrayList<String> list) {

        if (list == null) {
            throw new IllegalArgumentException("The argument list must not be null!");
        }

        Object[] objArray = list.toArray();
        String[] strArray = new String[objArray.length];

        for (int i = 0; i < objArray.length; i++) {

            if (objArray[i] == null) {
                strArray[i] = null;
                continue;
            }

            if (objArray[i] instanceof String) {
                strArray[i] = (String) objArray[i];
            } else {
                strArray[i] = objArray.toString();
            }
        }

        return strArray;
    }

    public static String listToString(ArrayList<String> list) {

        if (list == null) {
            throw new IllegalArgumentException("The argument list must not be null!");
        }

        Object[] objArray = list.toArray();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < objArray.length; i++) {

            if (objArray[i] == null) {
                continue;
            }

            buffer.append(objArray[i].toString());
            buffer.append(' ');
        }

        return buffer.toString();
    }
}
