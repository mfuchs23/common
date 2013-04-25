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
import org.dbdoclet.service.StringServices;

/**
 * Die Klasse <code>ExtensionFileFilter</code> realisiert einen Filter für die
 * Klasse <code>JFileChooser</code>. Die Dateien werden nach ihrer Erweiterung
 * gefiltert, z.B. ".zip".
 * 
 * @author <a href="mailto:michael.fuchs@unico-group.com">Michael Fuchs</a>
 * @version 1.0
 */
public class ExtensionFileFilter extends FileFilter {

	private static Log logger = LogFactory.getLog(ExtensionFileFilter.class);

	private String description;
	private String[] extensions;

	public ExtensionFileFilter(String extension) {

		if (extension == null) {
			throw new IllegalArgumentException(
					"The argument extension must not be null!");
		}

		String[] extensions = new String[1];
		extensions[0] = extension;

		init(extensions, extension);
	}

	public ExtensionFileFilter(String extension, String description) {

		if (extension == null) {
			throw new IllegalArgumentException(
					"The argument extension must not be null!");
		}

		if (description == null) {
			throw new IllegalArgumentException(
					"The argument description must not be null!");
		}

		String[] extensions = new String[1];
		extensions[0] = extension;

		init(extensions, extension);
	}

	public ExtensionFileFilter(String extensions[], String description) {

		if (description == null) {
			throw new IllegalArgumentException(
					"The argument description must not be null!");
		}

		if (extensions == null || extensions.length == 0) {
			throw new IllegalArgumentException(
					"The argument extension must not be null or empty!");
		}

		init(extensions, description);
	}

	private void init(String[] extensions, String description) {

		for (int i = 0; i < extensions.length; i++) {

			extensions[i] = extensions[i].trim();
			extensions[i] = StringServices.cutPrefix(extensions[i], ".");
			extensions[i] = extensions[i].toLowerCase();
		}

		this.description = description;
		this.extensions = extensions;
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
		ext = StringServices.cutPrefix(ext, ".");
		ext = ext.toLowerCase();

		for (int i = 0; i < extensions.length; i++) {

			if (ext.equals(extensions[i])) {
				return true;
			}
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
