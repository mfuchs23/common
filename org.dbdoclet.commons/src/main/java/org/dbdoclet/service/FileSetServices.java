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
package org.dbdoclet.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.io.FileSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FileSetServices {

	private static Log logger = LogFactory.getLog(FileSetServices.class
			.getName());

	public static void createAntFileSets(Document doc, Element parent,
			ArrayList<FileSet> fileSetList) throws IOException {

		if (doc == null) {
			throw new IllegalArgumentException(
					"The argument doc may not be null!");
		}

		if (parent == null) {
			throw new IllegalArgumentException(
					"The argument parent may not be null!");
		}

		if (fileSetList == null) {
			throw new IllegalArgumentException(
					"The argument fileSetList may not be null!");
		}

		for (FileSet fileSet : fileSetList) {

			Element elem = createAntFileSet(doc, parent, fileSet);
			parent.appendChild(elem);
		}
	}

	public static Element createAntFileSet(Document doc, Element parent,
			FileSet fileSet) {

		if (doc == null) {

			throw new IllegalArgumentException(
					"The argument doc may not be null!");
		}

		if (fileSet == null) {
			throw new IllegalArgumentException(
					"The argument fileSet may not be null!");
		}

		Element elem = null;

		String filter = fileSet.getFilter();

		if (filter == null) {
			filter = "";
		}

		Element include;
		Element exclude;

		boolean isJavadocParent = false;
		String tagName = parent.getTagName();

		if (tagName != null && tagName.equalsIgnoreCase("javadoc") == true) {
			isJavadocParent = true;
		}

		logger.debug("filter type = " + fileSet.getFilterType());

		File path = fileSet.getQualifiedPath();

		if (path.isFile()) {

			if (parent.getTagName().equals("path")) {

				elem = doc.createElement("pathelement");
				elem.setAttribute("location", fileSet.getRelativePath()
						.getPath());

			} else {

				elem = doc.createElement("fileset");
				elem.setAttribute("dir", path.getParentFile().getPath());
				include = doc.createElement("include");
				include.setAttribute("name", path.getName());
				elem.appendChild(include);
			}
		} else {

			switch (fileSet.getFilterType()) {

			case FileSet.FILTER_NONE:

				if (isJavadocParent == true) {
					elem = doc.createElement("packageset");
					elem.setAttribute("dir", fileSet.getDirName());
				} else {
					elem = doc.createElement("fileset");
					elem.setAttribute("dir", fileSet.getDirName());
				}

				break;

			case FileSet.FILTER_INCLUDE_FILES:
				elem = doc.createElement("fileset");
				elem.setAttribute("dir", fileSet.getDirName());
				include = doc.createElement("include");
				include.setAttribute("name", fileSet.getFilter());
				elem.appendChild(include);
				break;

			case FileSet.FILTER_EXCLUDE_FILES:
				elem = doc.createElement("fileset");
				elem.setAttribute("dir", fileSet.getDirName());
				exclude = doc.createElement("exclude");
				exclude.setAttribute("name", fileSet.getFilter());
				elem.appendChild(exclude);
				break;

			case FileSet.FILTER_INCLUDE_DIRECTORIES:
				elem = doc.createElement("dirset");
				elem.setAttribute("dir", fileSet.getDirName());
				include = doc.createElement("include");
				include.setAttribute("name", fileSet.getFilter());
				elem.appendChild(include);
				break;

			case FileSet.FILTER_EXCLUDE_DIRECTORIES:
				elem = doc.createElement("dirset");
				elem.setAttribute("dir", fileSet.getDirName());
				exclude = doc.createElement("exclude");
				exclude.setAttribute("name", fileSet.getFilter());
				elem.appendChild(exclude);
				break;

			case FileSet.FILTER_INCLUDE_PACKAGES:

				if (isJavadocParent == true) {
					elem = doc.createElement("packageset");
					elem.setAttribute("dir", fileSet.getDirName());
				} else {
					elem = doc.createElement("fileset");
					elem.setAttribute("dir", fileSet.getDirName());
				}

				include = doc.createElement("include");
				include.setAttribute("name", fileSet.getPackageFilter());
				elem.appendChild(include);
				break;

			case FileSet.FILTER_EXCLUDE_PACKAGES:
				elem = doc.createElement("packageset");
				elem.setAttribute("dir", fileSet.getDirName());
				exclude = doc.createElement("exclude");
				exclude.setAttribute("name", fileSet.getPackageFilter());
				elem.appendChild(exclude);
				break;
			}
		}

		if (elem != null && elem.getTagName().equals("pathelement") == false) {
			if (fileSet.isCaseSensitive() == false) {
				elem.setAttribute("casesensitive", "false");
			}
		}

		return elem;
	}

}
