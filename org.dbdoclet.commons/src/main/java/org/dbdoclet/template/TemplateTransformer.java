/* 
 * ### Copyright (C) 2005-2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import org.dbdoclet.service.StringServices;
 
public class TemplateTransformer {

    private BufferedReader reader;
    
    public static TemplateTransformer newInstance(File template)
        throws TemplateTransformException {

        if (template == null) {
            throw new IllegalArgumentException("The argument template may not be null!");
        }
 
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(template));
        } catch (IOException oops) {
            throw new TemplateTransformException(oops.getMessage(), oops);
        }

        return new TemplateTransformer(reader);
    }

    public static TemplateTransformer newInstance(BufferedReader template)
        throws TemplateTransformException {

        if (template == null) {
            throw new IllegalArgumentException("The argument template may not be null!");
        }
 
        return new TemplateTransformer(template);
    }

    public static TemplateTransformer newInstance(String template)
        throws TemplateTransformException {

        if (template == null) {
            throw new IllegalArgumentException("The argument template may not be null!");
        }
 
        return new TemplateTransformer(new BufferedReader(new StringReader(template)));
    }

    private TemplateTransformer(BufferedReader reader) {

        this.reader = reader;
    }

    public String transform(Map<String, String> vars)
        throws TemplateTransformException {

        StringWriter buffer = new StringWriter();
        
        transform(vars, new PrintWriter(buffer));

        return buffer.toString();
    }

    public void transform(Map<String, String> vars, String fileName)
        throws TemplateTransformException {

        if (fileName == null) {
            throw new IllegalArgumentException("The argument fileName must not be null!");
        }

        transform(vars, new File(fileName));
    }

    public void transform(Map<String, String> vars, File file)
        throws TemplateTransformException {

        if (file == null) {
            throw new IllegalArgumentException("The argument file must not be null!");
        }

        try {

            // if (file.getName().equals("default.css")) {
            //     trace = true;
            // }

            transform(vars, new FileWriter(file));

        } catch (IOException oops) {

            throw new TemplateTransformException(oops.getMessage(), oops);
        }
    }

    public void transform(Map<String, String> vars, FileWriter fileWriter)
        throws TemplateTransformException {

        if (fileWriter == null) {
            throw new IllegalArgumentException("The argument fileWriter must not be null!");
        }
        
        PrintWriter writer = new PrintWriter(fileWriter);
        transform(vars, writer);
        writer.close();
    }

    public void transform(Map<String, String> vars, PrintWriter writer)
        throws TemplateTransformException {

        if (reader == null) {
            throw new IllegalStateException("The field reader may not be null!");
        }
 
        if (vars == null) {
            throw new IllegalArgumentException("The argument vars may not be null!");
        }
 
        if (writer == null) {
            throw new IllegalArgumentException("The argument writer may not be null!");
        }
 
        try {

            Iterator<String> iterator;
            String name;
            Object value;
            
            String line;
            
            StringBuffer buffer = new StringBuffer();
            
            while ((line = reader.readLine()) != null ) {

                if (line.trim().length() == 0) {

                    buffer.append(line);
                    buffer.append('\n');
                    continue;
                }

                if (line.indexOf('$') == -1) {

                    buffer.append(line);
                    buffer.append('\n');
                    continue;
                }

                // if (trace) {
                //     System.out.println("line=" + line);
                // }

                iterator = vars.keySet().iterator();
                
                while (iterator.hasNext()) {
                    
                    name = iterator.next();
                    value = vars.get(name);
                    
                    if (value != null) {

                        name = "${" + name + "}";

                        // if (trace && name.equals("${css.hl1.font}")) {
                        //     System.out.println("name=" + name);
                        // }

                        line = StringServices.replace(line, name, value.toString());

                        // if (trace && name.equals("${css.hl1.font}")) {
                        //     System.out.println("new line=" + line);
                        // }

                    }
                }

                buffer.append(line);
                buffer.append('\n');
            }

            reader.close();

            // String str = ReplaceServices.replaceAll(buffer.toString(), "\\$\\{\\w*\\}", "");
            String str = buffer.toString();
            
            BufferedReader bufferReader = new BufferedReader(new StringReader(str));

            while ((line = bufferReader.readLine()) != null ) {
                writer.println(line);
            }
            
            bufferReader.close();
            
        } catch (Exception oops) {
            throw new TemplateTransformException(oops.getMessage(), oops);
        }
    }
}
