/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@dbdoclet.org
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.filter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.service.FileServices;

/**
 * Die Klasse <code>RegexpFileFilter</code> realisiert einen Filter für die
 * Klasse <code>JFileChooser</code>. Die Dateien werden nach einem regulärem
 * Ausdruck gefiltert, z.B. "\\.zip$".
 * 
 * @author <a href="mailto:michael.fuchs@unico-group.com">Michael Fuchs</a>
 * @version 1.0
 */
public class RegexpFileFilter extends FileFilter {

	private static Log logger = LogFactory.getLog(RegexpFileFilter.class);

	private final String description;
	private final String regexp;

	public RegexpFileFilter(String regexp, String description) {

		if (regexp == null) {
			throw new IllegalArgumentException(
					"The argument regexp must not be null!");
		}

		if (description == null) {
			throw new IllegalArgumentException(
					"The argument description must not be null!");
		}

		this.regexp = regexp;
		this.description = description;

		if (regexp.startsWith("^") == false) {
			regexp = "^.*" + regexp;
		}

		if (regexp.endsWith("$") == false) {
			regexp += ".*$";
		}
	}

	@Override
	public boolean accept(File file) {

		logger.debug("file=" + file);

		if (file == null) {
			return false;
		}

		if (file.isDirectory() == true) {
			return true;
		}

		String ext = FileServices.getExtension(file.getName());

		if (ext == null || ext.length() == 0) {
			return false;
		}

		ext = ext.trim();
		ext = ext.toLowerCase();

		if (ext.startsWith(".") == false) {
			ext = "." + ext;
		}

		if (ext.matches(regexp)) {
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return description;
	}
}
