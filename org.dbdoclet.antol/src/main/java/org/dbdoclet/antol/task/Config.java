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
package org.dbdoclet.antol.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.PropertyHelper;
import org.dbdoclet.service.ReplaceServices;
import org.dbdoclet.service.StringServices;

public class Config extends SokoTask {

	private static Log logger = LogFactory.getLog(Config.class);

	public void execute() throws BuildException {

		// System.out.println("Soko Konfiguration...");
		File file;

		try {

			super.validate();

			Project project = getProject();
			PropertyHelper helper = PropertyHelper.getPropertyHelper(project);

			File workbenchDir = findWorkbenchDirectory();
			String workbench = workbenchDir.getCanonicalPath();

			File workspaceDir = findWorkspaceDirectory();
			String workspace = workspaceDir.getCanonicalPath();

			String fileName = appendFileName(workspace, "build.properties");
			Properties props = new Properties();
			file = new File(fileName);

			if (file.exists() == true) {
				props.load(new FileInputStream(fileName));
			}

			String config = props.getProperty("config.name");

			if (config == null || config.length() == 0) {
				config = "";
			}

			String jars = appendFileName(workspace, "jars");
			String bundles = appendFileName(workspace, "bundles");
			String licenses = appendFileName(workspace, "licenses");

			String assembly = appendFileName(workspace, "assembly");
			String doc = appendFileName(workspace, "doc");
			String development = appendFileName(workbench, "development");
			String developmentJars = appendFileName(development, "jars");

			String ear = appendFileName(assembly, "ear");
			String ejbjars = appendFileName(ear, "ejb");
			String lib = appendFileName(assembly, "lib");
			String root = appendPath(assembly, "root");
			String runtime = appendPath(assembly, "runtime");
			String runtimeWar = appendPath(runtime, "war");
			String sql = appendPath(assembly, "sql");
			String src = appendPath(assembly, "src");
			String war = appendFileName(assembly, "war");
			String tgz = appendFileName(assembly, "tgz");
			String misc = appendFileName(assembly, "misc");

			String buildWar = appendFileName("build", "war");
			String buildEar = appendFileName("build", "ear");
			String buildEjbjars = appendFileName(buildEar, "ejb");

			String ode = appendFileName(workspace, "ode");

			String targets = appendFileName(ode, "ant");
			targets = appendFileName(targets, "targets");

			String docbook = appendFileName(ode, "docbook");

			String kits = appendFileName(workspace, "kits");
			String packman = appendFileName(workspace, "packman");

			if (config != null && config.length() > 0) {
				packman = appendFileName(packman, config);
				doc = appendFileName(doc, config);
			}

			helper.setNewProperty("", "assembly", assembly);
			helper.setNewProperty("", "assembly.bundles", bundles);
			helper.setNewProperty("", "assembly.doc", doc);
			helper.setNewProperty("", "assembly.ear", ear);
			helper.setNewProperty("", "assembly.ejbjars", ejbjars);
			helper.setNewProperty("", "assembly.lib", lib);
			helper.setNewProperty("", "assembly.root", root);
			helper.setNewProperty("", "assembly.runtime", runtime);
			helper.setNewProperty("", "assembly.runtime.war", runtimeWar);
			helper.setNewProperty("", "assembly.sql", sql);
			helper.setNewProperty("", "assembly.src", src);
			helper.setNewProperty("", "assembly.war", war);
			helper.setNewProperty("", "assembly.tgz", tgz);
			helper.setNewProperty("", "assembly.misc", misc);
			helper.setNewProperty("", "build", "build");
			helper.setNewProperty("", "build.ear", buildEar);
			helper.setNewProperty("", "build.ejbjars", buildEjbjars);
			helper.setNewProperty("", "build.war", buildWar);
			helper.setNewProperty("", "development", development);
			helper.setNewProperty("", "development.jars", developmentJars);
			helper.setNewProperty("", "export", "export");
			helper.setNewProperty("", "kits.dir", kits);
			helper.setNewProperty("", "ode.home", ode);
			helper.setNewProperty("", "ode.docbook", docbook);
			helper.setNewProperty("", "ode.targets.dir", targets);
			helper.setNewProperty("", "packman.dir", packman);
			helper.setNewProperty("", "workbench", workbench);
			helper.setNewProperty("", "workspace", workspace);
			helper.setNewProperty("", "workspace.jars", jars);
			helper.setNewProperty("", "workspace.licenses", licenses);

			HashMap<String, String> developmentJarMap = getDevelopmentJarMap();
			setJarProperties(helper, new File(jars), new File(jars),
					developmentJarMap);
			setJarProperties(helper, new File(lib), new File(lib),
					developmentJarMap);

			setOsProperties(helper);

		} catch (BuildException oops) {

			oops.printStackTrace();
			throw oops;

		} catch (Exception oops) {

			oops.printStackTrace();
			throw new BuildException(oops);
		}
	}

	private void setOsProperties(PropertyHelper helper) throws IOException {

		if (helper == null) {
			throw new IllegalArgumentException(
					"The argument helper may not be null!");
		}

		String os = System.getProperty("os.name");

		if (os.startsWith("Windows")) {

			String drive = (String) helper.getUserProperty("",
					"win.install.drive");
			if (drive == null || drive.length() == 0) {
				drive = "D:/";
			}

			String program = (String) helper.getUserProperty("",
					"win.program.dir");
			if (program == null || program.length() == 0) {
				program = "Programme";
			}

			helper.setNewProperty("", "os.root", drive);
			helper.setNewProperty("", "os.bin.dir", "Werkbank/bin");
			helper.setNewProperty("", "os.share.dir", program);
			helper.setNewProperty("", "install.root", drive);

		} else {

			helper.setNewProperty("", "os.root", "/");
			helper.setNewProperty("", "os.bin.dir", "usr/bin");
			helper.setNewProperty("", "os.share.dir", "usr/share");
			helper.setNewProperty("", "install.root", "/");
		}
	}

	private void setJarProperties(PropertyHelper helper, File jarsDir,
			File dir, HashMap<String, String> developmentJarMap) throws IOException {

		if (jarsDir == null) {
			throw new IllegalArgumentException(
					"The argument jarsDir may not be null!");
		}

		if (helper == null) {
			throw new IllegalArgumentException(
					"The argument helper may not be null!");
		}

		if (dir == null) {
			throw new IllegalArgumentException(
					"The argument dir must not be null!");
		}

		if (jarsDir.exists() == false || jarsDir.isDirectory() == false) {
			return;
		}

		if (dir.exists() == false || dir.isDirectory() == false) {
			return;
		}

		String jarsDirName = jarsDir.getCanonicalPath();
		jarsDirName += File.separator;

		File[] list = dir.listFiles();

		if (list != null) {

			String name;
			String lower;

			for (int i = 0; i < list.length; i++) {

				if (list[i].isFile()) {

					name = list[i].getCanonicalPath();
					name = StringServices.cutPrefix(name, jarsDirName);
					name = StringServices.replace(name, File.separator, "/");

					String devJar = (String) developmentJarMap.get(name);

					lower = name.toLowerCase();

					if (lower.endsWith(".jar") || lower.endsWith(".tld")) {

						if (devJar != null) {

							if (isVerbose()) {
								log("ACHTUNG: Verwende Entwicklungsversion der Bibliothek "
										+ name);
							}

							helper.setNewProperty("", name, devJar);

						} else {

							// System.out.println("JarArchiv: " + name + "=" + list[i].getCanonicalPath());
							helper.setNewProperty("", name,
									list[i].getCanonicalPath());

							name = ReplaceServices.replaceAll(name, "(_|-)[0-9\\.]+\\.jar$", ".jar");
							helper.setNewProperty("", name,
									list[i].getCanonicalPath());
						}
					}
				}

				if (list[i].isDirectory()) {

					logger.debug("Verzeichniswechsel nach " + list[i].getPath());
					setJarProperties(helper, jarsDir, list[i],
							developmentJarMap);
				}

			}
		}
	}
}
