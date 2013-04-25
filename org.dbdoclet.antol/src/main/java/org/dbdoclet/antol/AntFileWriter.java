/* 
 * $Id$
 *
 * ### Copyright (C) 2005 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 *
 * RCS Information
 * Author..........: $Author$
 * Date............: $Date$
 * Revision........: $Revision$
 * State...........: $State$
 */
package org.dbdoclet.antol;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dbdoclet.io.FileSet;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.FileSetServices;
import org.dbdoclet.xiphias.NodeSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AntFileWriter {

    private static final int COMMENT_LENGTH = 60;

    private Document doc;
    private File file;
    private Element project;

    public AntFileWriter(File file) throws IOException, ParserConfigurationException {

        if (file == null) {
            throw new IllegalArgumentException("Variable file is null!");
        }

        this.file = file;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parser = factory.newDocumentBuilder();
        doc = parser.newDocument();
    }

    public Element addAnt(Element parent, String path, String target) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (target == null)
            throw new IllegalArgumentException("Variable target is null!");

        Element elem = addElement(parent, "ant");
        elem.setAttribute("dir", path);
        elem.setAttribute("target", target);

        return elem;
    }

    public Element addAntCall(Element parent, String target) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (target == null)
            throw new IllegalArgumentException("Variable target is null!");

        Element elem = addElement(parent, "antcall");
        elem.setAttribute("target", target);

        return elem;
    }

    public Element addArg(Element parent, String value) {

        if (parent == null) {
            throw new IllegalArgumentException("The argument parent may not be null!");
        }

        if (value == null) {
            throw new IllegalArgumentException("The argument value may not be null!");
        }

        Element arg = addElement(parent, "arg");
        arg.setAttribute("value", value);

        return arg;
    }

    public Element addBootclasspath(Element parent, String refid) {

        if (parent == null) {
            throw new IllegalArgumentException("Variable parent is null!");
        }

        if (refid == null) {
            throw new IllegalArgumentException("Variable refid is null!");
        }

        Element elem = addElement(parent, "bootclasspath");
        elem.setAttribute("refid", refid);

        return elem;
    }

    public Element addClasspath(Element parent, String refid) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (refid == null)
            throw new IllegalArgumentException("Variable refid is null!");

        Element elem = addElement(parent, "classpath");
        elem.setAttribute("refid", refid);

        return elem;
    }

    public void addComment(Element parent, String comment) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (comment == null)
            comment = "";

        for (int i = comment.length(); i < COMMENT_LENGTH; i++)
            comment += " ";

        parent.appendChild(doc.createComment(comment));
    }

    public void addCommentSeparator(Element parent) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        parent.appendChild(doc.createComment(getCommentSeparator()));
    }

    public Element addCopy(Element parent, String directory) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (directory == null)
            throw new IllegalArgumentException("Variable directory is null!");

        Element elem = addElement(parent, "copy");
        elem.setAttribute("todir", directory);

        return elem;
    }

    public Element addCopy(Element parent, String file, String directory) {

        if (parent == null) {
            throw new IllegalArgumentException("The argument parent must not be null!");
        }

        if (file == null) {
            throw new IllegalArgumentException("The argument file must not be null!");
        }

        if (directory == null) {
            throw new IllegalArgumentException("The argument directory must not be null!");
        }

        Element elem = addElement(parent, "copy");
        elem.setAttribute("file", file);
        elem.setAttribute("todir", directory);

        return elem;
    }

    public Element addDelete(Element parent, String dir) {

        if (parent == null) {
            throw new IllegalArgumentException("The argument parent may not be null!");
        }

        if (dir == null) {
            throw new IllegalArgumentException("The argument dir may not be null!");
        }

        Element delete = addElement(parent, "delete");
        delete.setAttribute("dir", dir);

        return delete;
    }

    public Element addDelete(Element parent, String dir, String include) {

        Element delete = addElement(parent, "delete");
        addFileset(delete, dir, include);

        return delete;
    }

    public Element addDoclet(Element parent, String name, String path) {
        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (name == null)
            throw new IllegalArgumentException("Variable name is null!");

        if (path == null)
            throw new IllegalArgumentException("Variable path is null!");

        Element doclet = addElement(parent, "doclet");
        doclet.setAttribute("name", name);
        doclet.setAttribute("path", path);
        parent.appendChild(doclet);

        return doclet;
    }

    public Element addElement(Element parent, Element elem) {

        if (parent == null) {
            throw new IllegalArgumentException("Variable parent is null!");
        }

        if (elem == null) {
            throw new IllegalArgumentException("Variable elem is null!");
        }

        parent.appendChild(elem);

        return elem;
    }

    public Element addElement(Element parent, String name) {

        if (parent == null) {
            throw new IllegalArgumentException("Variable parent is null!");
        }

        if (name == null) {
            throw new IllegalArgumentException("Variable name is null!");
        }

        Element elem = doc.createElement(name);
        parent.appendChild(elem);

        return elem;
    }

    public Element addFileProperty(Element elem, String name, String value) {

        if (elem == null) {
            throw new IllegalArgumentException("The argument elem must not be null!");
        }

        if (name == null) {
            throw new IllegalArgumentException("The argument name must not be null!");
        }

        if (value == null) {
            throw new IllegalArgumentException("The argument value must not be null!");
        }

        value = FileServices.normalizePath(value);
        return addProperty(elem, name, value);
    }

    public Element addFileset(Element parent, String dir) {
        return addFileset(parent, dir, null);
    }

    public Element addFileset(Element parent, String dir, String filter) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (dir == null)
            throw new IllegalArgumentException("Variable dir is null!");

        Element fileset = addElement(parent, "fileset");
        fileset.setAttribute("dir", dir);

        if (filter != null && filter.length() > 0) {
            Element include = addElement(fileset, "include");
            include.setAttribute("name", filter);
        }

        return fileset;
    }

    public Element addFilterset(Element parent, HashMap<String, String> tokens) {

        if (parent == null) {
            throw new IllegalArgumentException("Variable parent is null!");
        }

        Element filterset = addElement(parent, "filterset");
        for (String token : tokens.keySet()) {

            Element filter = addElement(filterset, "filter");
            filter.setAttribute("token", token);
            filter.setAttribute("value", tokens.get(token));
        }

        return filterset;
    }

    public Element addFop(Element parent, String format, String basedir, String fofile, String outfile) {

        if (parent == null) {
            throw new IllegalArgumentException("The argument parent may not be null!");
        }

        addComment(parent, " TASK fop ");

        Element fop = addElement(parent, "fop");
        fop.setAttribute("format", format);
        fop.setAttribute("basedir", basedir);
        fop.setAttribute("fofile", fofile);
        fop.setAttribute("outfile", outfile);

        return fop;
    }

    public Element addJava(Element parent) {

        if (parent == null) {
            throw new IllegalArgumentException("The argument parent may not be null!");
        }

        addComment(parent, " TASK java ");

        Element java = addElement(parent, "java");
        java.setAttribute("failonerror", "yes");
        java.setAttribute("fork", "yes");
        java.setAttribute("maxmemory", "512m");

        return java;
    }

    public Element addJavadoc(Element parent) {
        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        addComment(parent, " TASK javadoc ");

        Element javadoc = addElement(parent, "javadoc");
        javadoc.setAttribute("failonerror", "yes");
        javadoc.setAttribute("maxmemory", "512m");

        return javadoc;
    }

    public Element addJvmarg(Element parent, String value) {

        if (parent == null) {
            throw new IllegalArgumentException("The argument parent may not be null!");
        }

        if (value == null) {
            throw new IllegalArgumentException("The argument value may not be null!");
        }

        Element arg = addElement(parent, "jvmarg");
        arg.setAttribute("value", value);

        return arg;
    }

    public Element addMessage(Element parent, String msg) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (msg == null)
            msg = "";

        Element echo = addElement(parent, "echo");
        echo.setAttribute("message", msg);

        return echo;
    }

    public Element addMkDir(Element parent, String directory) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (directory == null)
            throw new IllegalArgumentException("Variable directory is null!");

        Element elem = addElement(parent, "mkdir");
        elem.setAttribute("dir", directory);

        return elem;
    }

    public Element addMove(Element parent, String from, String to) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (from == null)
            throw new IllegalArgumentException("Variable from is null!");

        if (to == null)
            throw new IllegalArgumentException("Variable to is null!");

        Element elem = addElement(parent, "move");
        elem.setAttribute("file", from);
        elem.setAttribute("tofile", to);

        return elem;
    }

    public void addPackages(Element parent, ArrayList<String> packages) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (packages == null)
            throw new IllegalArgumentException("Variable packages is null!");

        Element elem;
        String str;

        if (packages != null && packages.size() > 0) {

            for (int i = 0; i < packages.size(); i++) {

                str = (String) packages.get(i);

                elem = addElement(parent, "package");
                elem.setAttribute("name", str);

            } // end of while ()
        }
    }

    public Element addParam(Element parent, String name, String value) {

        if (parent == null) {
            throw new IllegalArgumentException("The argument parent may not be null!");
        }

        if (name == null) {
            throw new IllegalArgumentException("The argument name may not be null!");
        }

        Element param = addElement(parent, "param");
        param.setAttribute("name", name);

        if (value != null) {
            param.setAttribute("value", value);
        }

        return param;
    }

    public Element addPath(Element parent, String id, ArrayList<FileSet> pathlist) throws IOException {

        if (parent == null) {
            throw new IllegalArgumentException("Variable parent is null!");
        }

        if (id == null) {
            throw new IllegalArgumentException("Variable id is null!");
        }

        if (pathlist == null) {
            throw new IllegalArgumentException("Variable pathlist is null!");
        }

        addComment(parent, " PATH " + id);

        Element path;
        path = doc.createElement("path");
        path.setAttribute("id", id);

        parent.appendChild(path);

        if (pathlist != null && pathlist.size() > 0) {
            FileSetServices.createAntFileSets(doc, path, pathlist);
        }

        return path;
    }

    public Element addPropertiesFile(Element elem, String fileName) {

        if (elem == null) {
            throw new IllegalArgumentException("The argument elem may not be null!");
        }

        if (fileName == null) {
            throw new IllegalArgumentException("The argument fileName may not be null!");
        }

        fileName = FileServices.normalizePath(fileName);

        Element property = doc.createElement("property");
        property.setAttribute("file", fileName);
        elem.appendChild(property);

        return property;
    }

    public Element addProperty(Element elem, String name, String value) {

        if (elem == null)
            throw new IllegalArgumentException("Variable elem is null!");

        if (name == null)
            throw new IllegalArgumentException("Variable name is null!");

        if (value == null)
            throw new IllegalArgumentException("Variable value is null!");

        Element property = doc.createElement("property");
        property.setAttribute("name", name);

        if (value != null) {
            property.setAttribute("value", value);
        }

        elem.appendChild(property);

        return property;
    }

    public Element addSourcepath(Element parent, String refid) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (refid == null)
            throw new IllegalArgumentException("Variable refid is null!");

        Element elem = addElement(parent, "sourcepath");
        elem.setAttribute("refid", refid);

        return elem;
    }

    public Element addTarget(String name) {

        if (name == null)
            throw new IllegalArgumentException("Variable name is null!");

        if (project == null)
            throw new IllegalStateException("Variable project is null!");

        Element target = doc.createElement("target");
        target.setAttribute("name", name);

        addCommentSeparator(target);
        addComment(target, " TARGET " + name);
        addCommentSeparator(target);

        project.appendChild(target);

        return target;
    }

    public Element addTaskDef(String name) {

        if (name == null) {
            throw new IllegalArgumentException("Variable name is null!");
        }

        if (project == null) {
            throw new IllegalStateException("Variable project is null!");
        }

        Element taskdef = doc.createElement("taskdef");
        taskdef.setAttribute("name", name);
        project.appendChild(taskdef);

        if (name.equals("fop") == true) {

            taskdef.setAttribute("classname", "org.apache.fop.tools.anttasks.Fop");

            Element classpath = doc.createElement("classpath");
            taskdef.appendChild(classpath);

            addFileset(classpath, "${fop.home}/lib", "*.jar");
            addFileset(classpath, "${fop.home}/build", "fop*.jar");
        }

        return taskdef;
    }

    public Element addTextPath(Element parent, String id, ArrayList<String> pathlist) throws IOException {

        if (parent == null) {
            throw new IllegalArgumentException("Variable parent is null!");
        }

        if (id == null) {
            throw new IllegalArgumentException("Variable id is null!");
        }

        if (pathlist == null) {
            throw new IllegalArgumentException("Variable pathlist is null!");
        }

        Element path;
        Element pathelement;
        String str;
        Object obj;

        addComment(parent, " PATH " + id);

        path = doc.createElement("path");
        path.setAttribute("id", id);

        parent.appendChild(path);

        if (pathlist != null && pathlist.size() > 0) {

            for (int i = 0; i < pathlist.size(); i++) {

                obj = pathlist.get(i);

                if (obj instanceof String) {

                    str = (String) obj;

                    pathelement = doc.createElement("pathelement");

                    pathelement.setAttribute("path", str);
                    path.appendChild(pathelement);
                }
            }
        }

        return path;
    }

    public Element addXslt(Element parent, String in, String style, String out) {

        if (parent == null)
            throw new IllegalArgumentException("Variable parent is null!");

        if (in == null)
            throw new IllegalArgumentException("Variable in is null!");

        if (style == null)
            throw new IllegalArgumentException("Variable style is null!");

        if (out == null)
            throw new IllegalArgumentException("Variable out is null!");

        addComment(parent, " TASK xslt ");

        Element xslt = addElement(parent, "xslt");
        xslt.setAttribute("in", in);
        xslt.setAttribute("style", style);
        xslt.setAttribute("out", out);

        return xslt;
    }

    private String getCommentSeparator() {

        String sep = " ";

        for (int i = 2; i < COMMENT_LENGTH; i++) {
            sep += "=";
        }

        sep += " ";

        return sep;
    }

    public Document getDocument() {
        return doc;
    }

    public void save() throws IOException {

        if (doc == null) {
            return;
        }

        NodeSerializer serializer = new NodeSerializer();
        serializer.write(doc, file);
    }

    public Element setProject(String name, String target) {

        if (name == null)
            throw new IllegalArgumentException("Variable name is null!");

        if (target == null)
            throw new IllegalArgumentException("Variable target is null!");

        Element project = doc.createElement("project");

        project.setAttribute("name", name);
        project.setAttribute("default", target);

        // doc.appendChild(doc.createComment("dbdoclet"));
        doc.appendChild(project);

        this.project = project;

        DateFormat df = DateFormat.getDateTimeInstance();

        addCommentSeparator(project);
        addComment(project, " Generated from dbdoclet.TiDbit");
        addComment(project, " Generated at " + df.format(new Date()));
        addComment(project, " http://www.dbdoclet.org");
        addCommentSeparator(project);

        return project;
    }
}
/*
 * $Log$
 */
