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
package org.dbdoclet.jive.verifier;

import java.io.File;
import java.util.ResourceBundle;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.service.FileServices;

public class FileVerifier extends InputVerifier {

	private static Log logger = LogFactory.getLog(FileVerifier.class);

	private final File refFile;

	/**
	 * Erzeugt eine neue Instanz der Klasse <code>FileVerifier</code>.
	 * 
	 * @param res
	 *            <code>ResourceBundle</code>
	 * @param refFile
	 *            Die Referenzdatei für relative Pfade.
	 */
	public FileVerifier(ResourceBundle res, File refFile) {

		if (res == null) {
			throw new IllegalArgumentException("Parameter res is null!");
		}

		this.refFile = refFile;
	}

	@Override
	public boolean verify(JComponent input) {

		if (input == null) {
			throw new IllegalArgumentException("Parameter input is null!");
		}

		if (input instanceof JTextField) {

			JTextField entry = (JTextField) input;
			String path = entry.getText();

			logger.debug("path #1 = " + path);

			if ((path == null) || (path.length() == 0)) {
				return true;
			}

			if (path.matches("^.*\\$\\{.*\\}.*$")) {
				return true;
			}

			if (FileServices.isAbsolutePath(path) == false && refFile != null) {

				File parent = refFile.getParentFile();

				if (parent != null) {
					path = FileServices.appendPath(parent.getAbsolutePath(),
							path);
				}
			}

			logger.debug("path #2 = " + path);

			File file = new File(path);

			if (file.isFile()) {

				return true;

			} else {

				return false;
			}
		}

		return false;
	}
}
/*
 * $Log$
 */
