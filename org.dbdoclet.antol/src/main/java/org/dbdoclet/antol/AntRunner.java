/* 
 * ### Copyright (C) 2005-2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.antol;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.dbdoclet.io.Screen;
import org.dbdoclet.service.ResourceServices;

public class AntRunner extends Thread {

	public static final int DEBUG = 5;
	public static final int VERBOSE = 4;
	public static final int INFO = 3;
	public static final int WARN = 2;
	public static final int ERROR = 1;

	private static Log logger = LogFactory.getLog(AntRunner.class);
	private static ResourceBundle res = PropertyResourceBundle
			.getBundle("org/dbdoclet/antol/Resources");

	private File projectFile;
	private Map<String, String> properties;
	private ResultListener resultListener;
	private Screen screen;
	private String stacktrace = "";
	private Throwable oops;
	private Vector<String> projectTargets;
	private boolean buildFailed;
	private int verbosity = INFO;

	public AntRunner(File projectFile, String projectTarget) {

		Vector<String> targets = new Vector<String>();
		targets.add(projectTarget);

		init(null, projectFile, targets, null);
	}

	public AntRunner(File projectFile, Vector<String> projectTargets) {

		init(null, projectFile, projectTargets, null);
	}

	public AntRunner(File projectFile, String projectTarget,
			Map<String, String> properties) {

		Vector<String> targets = new Vector<String>();
		targets.add(projectTarget);

		init(null, projectFile, targets, properties);
	}

	public AntRunner(Screen screen, File projectFile, String projectTarget) {

		Vector<String> targets = new Vector<String>();
		targets.add(projectTarget);

		init(screen, projectFile, targets, null);
	}

	public AntRunner(Screen screen, File projectFile,
			Vector<String> projectTargets) {

		init(screen, projectFile, projectTargets, null);
	}

	public AntRunner(Screen screen, File projectFile,
			Vector<String> projectTargets, Map<String, String> properties) {

		init(screen, projectFile, projectTargets, properties);
	}

	public AntRunner(Screen screen, File projectFile, String projectTarget,
			Map<String, String> properties) {

		Vector<String> targets = new Vector<String>();
		targets.add(projectTarget);

		init(screen, projectFile, targets, properties);
	}

	private void init(Screen screen, File projectFile,
			Vector<String> projectTargets, Map<String, String> properties) {

		if (projectFile == null) {
			throw new IllegalArgumentException(
					"The argument projectFile may not be null!");
		}

		if (projectTargets == null) {
			throw new IllegalArgumentException(
					"The argument projectTargets may not be null!");
		}

		this.screen = screen;
		this.projectFile = projectFile;
		this.projectTargets = projectTargets;
		this.properties = properties;

		if (this.properties == null) {
			this.properties = new HashMap<String, String>();
		}

	}

	public void setVerbosity(int verbosity) {
		this.verbosity = verbosity;
	}

	public int getVerbosity() {
		return verbosity;
	}

	public void addProperty(String name, String value) {

		if (name == null) {
			throw new IllegalArgumentException(
					" The argument name may not be null!");
		}

		if (value == null) {
			throw new IllegalArgumentException(
					" The argument value may not be null!");
		}

		if (properties == null) {
			throw new IllegalStateException(
					" The field properties may not be null!");
		}

		properties.put(name, value);
	}

	public boolean failed() {
		return buildFailed;
	}

	public boolean getBuildFailed() {
		return buildFailed;
	}

	public String getStackTraceAsString() {

		return stacktrace;
	}

	public void setResultListener(ResultListener listener) {

		this.resultListener = listener;
	}

	@Override
	public void run() {

		logger.debug("Starte AntRunner...");

		Project project = null;

		buildFailed = true;
		oops = null;

		stacktrace = "";

		try {

			String str;

			if (projectFile == null) {

				error(ResourceServices.getString(res,
						"C_ERROR_PROJECT_FILE_IS_NOT_DEFINED"));
				return;

			}

			if (projectFile.exists() == false) {

				str = ResourceServices.getString(res,
						"C_ERROR_PROJECT_FILE_DOESNT_EXIST");
				str = MessageFormat.format(str, projectFile.getAbsolutePath());

				error(str);

				return;
			}

			logger.debug("Neues Projekt...");

			project = new Project();

			if (screen != null) {

				project.addBuildListener(new ScreenListener(screen, verbosity));

			} else {

				DefaultLogger buildListener = new DefaultLogger();
				buildListener.setMessageOutputLevel(Project.MSG_INFO);
				buildListener.setOutputPrintStream(System.out);
				buildListener.setErrorPrintStream(System.err);

				project.addBuildListener(buildListener);
			}

			project.init();

			project.setUserProperty("ant.file", projectFile.getAbsolutePath());

			String name;
			String value;

			if (properties != null) {

				for (Iterator<String> i = properties.keySet().iterator(); i
						.hasNext();) {

					name = i.next();
					value = properties.get(name);

					project.setUserProperty(name, value);
				}
			}

			logger.debug("Projektdatei parsen...");

			ProjectHelper helper = ProjectHelper.getProjectHelper();
			helper.parse(project, projectFile);

			buildFailed = false;

			try {

				logger.debug("Execute targets...");
				logger.debug("Classpath: "
						+ System.getProperty("java.class.path"));

				project.fireBuildStarted();
				project.executeTargets(projectTargets);
				project.fireBuildFinished(null);

			} catch (BuildException oops) {

				setStackTrace(oops);

				buildFailed = true;
				this.oops = oops;

				project.fireBuildFinished(oops);
			}

		} catch (Throwable oops) {

			logger.fatal("AntRunner", oops);

			buildFailed = true;
			this.oops = oops;

			setStackTrace(oops);

			if (screen != null) {
				screen.exception(oops);
			}
		}

		if (resultListener != null) {
			resultListener.finished(buildFailed, oops);
		}

	}

	private void setStackTrace(Throwable oops) {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(buffer);
		oops.printStackTrace(writer);
		writer.flush();
		writer.close();

		stacktrace = buffer.toString();
	}

	private void error(String msg) {

		if (screen != null) {
			screen.error(msg);
		} else {
			logger.fatal(msg);
		} // end of else
	}
}
