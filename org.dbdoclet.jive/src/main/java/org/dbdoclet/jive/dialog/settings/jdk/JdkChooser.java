/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.settings.jdk;

import java.io.File;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.JvmServices;
import org.dbdoclet.service.ResourceServices;

public class JdkChooser extends JFileChooser {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(JdkChooser.class);

	public ResourceBundle res;

	public JdkChooser(File dir, ResourceBundle res) {

		super(dir);

		this.res = res;
	}

	@Override
	public void approveSelection() {

		File dir = getSelectedFile();

		File binDir;
		File libDir;
		File jreDir;
		File javadocFile;
		String msg;
		String path;

		path = FileServices.appendPath(dir, "bin");
		binDir = new File(path);

		path = FileServices.appendPath(dir, "lib");
		libDir = new File(path);

		path = FileServices.appendPath(dir, "jre");
		jreDir = new File(path);

		path = FileServices.appendPath(dir, "bin");

		if (JvmServices.isWindows()) {
			path = FileServices.appendFileName(binDir, "javadoc.exe");
		} else {
			path = FileServices.appendFileName(binDir, "javadoc");
		}

		javadocFile = new File(path);

		if (binDir.exists() == false || libDir.exists() == false
				|| jreDir.exists() == false || javadocFile.exists() == false) {

			msg = MessageFormat.format(
					ResourceServices.getString(res, "C_ERROR_NO_JAVA_HOME"),
					dir.getAbsolutePath());
			ErrorBox.show(ResourceServices.getString(res, "C_ERROR"), msg);

			logger.error("Directory " + dir.getAbsolutePath()
					+ " seems not to be a JDK home.\n" + " bin ("
					+ binDir.getAbsolutePath() + ", " + binDir.exists() + ")\n"
					+ " lib (" + libDir.getAbsolutePath() + ", "
					+ libDir.exists() + ")\n" + " jre ("
					+ jreDir.getAbsolutePath() + ", " + jreDir.exists() + ")\n"
					+ " javadoc (" + javadocFile.getAbsolutePath() + ","
					+ javadocFile.exists() + ")");

			return;
		}

		logger.debug("approve selection: " + dir);
		super.approveSelection();
	}
}
