/* 
 * $Id$
 *
 * ### Copyright (C) 2005-2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.antol.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.dbdoclet.antol.AntRunner;
import org.dbdoclet.log.ScreenLogger;
import org.dbdoclet.service.FileServices;

/**
 * Die Assemble Task verarbeitet die Projektkonfiguration, die in
 * projects/${projectname}/${projectname}.conf beschrieben ist. Diese
 * Projektkonfigurationen werden wie folgt aufgebaut:
 * 
 * cnt component path version type namespace stage *
 * 
 * @author Michael Fuchs
 * 
 */
public class Assemble extends SokoTask {

	private static final String BRANCH_DELIM = "_";

	private static Log logger = LogFactory.getLog(Assemble.class.getName());

	private String type;
	private String name;
	private Vector<String> targets;
	private String root;
	private String conf;

	/**
	 * Main-Methode der Ant task. Zuerst wird die Projekt-konfiguration
	 * validiert, dann eine Modulliste gelesen. In Abhängigkeit vom Typ
	 * (process-resources oder ein anderer) wird im Anschluss entweder mit der
	 * Modulliste der process-resources-Vorgang ausgelöst oder die
	 * {@link Assemble#stage(ArrayList, String, Vector)} Methode aufgerufen.
	 * 
	 * @see org.apache.tools.ant.Task#execute()
	 */
	@Override
	public void execute() throws BuildException {
		validate();
		try {

			ArrayList<Module> moduleList = getModuleList();

			logger.info("Stage " + type);

			if (type.equals("process-resources") == true) {
				processResources(moduleList);
			} else {
				stage(moduleList, type, targets);
			}

		} catch (BuildException oops) {

			oops.printStackTrace();
			throw oops;

		} catch (Throwable oops) {

			oops.printStackTrace();
			throw new BuildException(oops);
		}
	}

	@SuppressWarnings("unchecked")
	private void stage(ArrayList<Module> moduleList, String stage,
			Vector<String> targets) throws IOException {

		if (moduleList == null) {
			throw new IllegalArgumentException(
					"The argument moduleList may not be null!");
		}

		if (stage == null) {
			throw new IllegalArgumentException(
					"The argument stage may not be null!");
		}

		if (targets == null) {
			throw new IllegalArgumentException(
					"The argument targets may not be null!");
		}

		Project project = getProject();
		String baseDir = project.getBaseDir().getPath();

		AntRunner runner;
		File logFile;
		Module module;
		PrintWriter writer;
		ScreenLogger screen;
		String arch;
		String key;
		String pkgName;
		String str;
		String value;

		String logPath = appendPath(baseDir, "log");
		createPath(logPath);

		HashMap<String, String> includeMap = new HashMap<String, String>();

		String include = project.getProperty("module.include");

		if (include != null && include.length() > 0) {

			StringTokenizer stz = new StringTokenizer(include, ",");
			String token;

			while (stz.hasMoreTokens()) {
				token = stz.nextToken();
				includeMap.put(token, include);
			}
		}

		Vector<String> moduleTargets = new Vector<String>();

		for (Iterator<Module> iterator = moduleList.iterator(); iterator
				.hasNext();) {

			moduleTargets.clear();
			moduleTargets.addAll(targets);

			module = iterator.next();

			if (name != null && name.length() > 0) {

				if (name.equals(module.getLibName()) == false) {
					continue;
				}
			}

			arch = module.getType();

			if (arch != null && isBinaryType(arch)) {
				continue;
			}

			if (includeMap.size() > 0) {

				int pos = module.getPos();
				if (includeMap.get(String.valueOf(pos)) == null
						&& includeMap.get(module.getLibName()) == null) {
					continue;
				}
			}

			if (stage.equals(module.getLevel()) || stage.equals("*")) {

				if (module.hasDescription()) {
					log(module.getDescription());
				}

				HashMap<String, String> props = new HashMap<String, String>();

				if (root != null && root.length() > 0) {
					props.put("install.root", root);
				}

				pkgName = module.getPackageName();

				if (pkgName == null) {
					pkgName = "*";
				}

				pkgName = pkgName.trim();

				if (pkgName.length() == 0) {
					pkgName = "*";
				}

				/*
				 * System.out.println("module=" + module.getName());
				 * System.out.println("type=" + module.getType());
				 * System.out.println("pkgName=" + pkgName);
				 * System.out.println("targets.size=" + targets.size());
				 */

				if (pkgName.equals("*") == false) {

					props.put("package.name", pkgName);

					if (moduleTargets.size() == 0) {
						moduleTargets.add(type + "-" + pkgName);
					}

				} else {

					props.put("package.name", "package.name");
				}

				if (moduleTargets.size() == 0) {
					moduleTargets.add(type);
				}

				Map<String, String> userProps = project.getUserProperties();
				Iterator<String> keyIterator = userProps.keySet().iterator();

				while (keyIterator.hasNext()) {

					key = keyIterator.next();
					value = userProps.get(key);

					if (key.startsWith("ant.") == false) {
						props.put(key, value);
					}
				}

				str = module.getLibName() + " (";

				for (int i = 0; i < moduleTargets.size(); i++) {
					str += " " + moduleTargets.get(i);
				}

				log(str + " )");

				logFile = new File(appendFileName(logPath, module.getLibName()
						+ "-" + moduleTargets.get(0) + ".log"));

				writer = new PrintWriter(new FileWriter(logFile));
				screen = new ScreenLogger(writer);

				runner = new AntRunner(screen, module.getBuildFile(),
						moduleTargets, props);
				runner.start();

				try {

					runner.join();

				} catch (InterruptedException oops) {

					writer.close();
					throw new BuildException("Interrupted!");
				}

				writer.close();

				if (runner.failed()) {

					printLog(logFile);
					System.err.println(runner.getStackTraceAsString());

					throw new BuildException("Die Generierung des Moduls "
							+ module.getLibName() + " ist fehlgeschlagen!");
				}
			}
		}
	}

	/**
	 * In dier Methode wernde doie Bestandteile eines Projektes zusammengestellt
	 * und aus dem Software-Repository geholt. Hierfür wird über alle Module
	 * iteriert und in Abhängigkeit vom Typ des Moduls entweder der das Modul
	 * aus dem Repository geholt, oder ein neuer Ant-Prozess auf die
	 * process-resources-target des Moduls aufgerufen.
	 * 
	 * Handelt es sich bei dem Typ um
	 * <ul>
	 * <li>dev</li>
	 * <li>binary</li>
	 * <li>war</li>
	 * <li>ear</li>
	 * <li>tgz</li>
	 * <li>misc</li>
	 * </ul>
	 * 
	 * so werden die Module entsprechend ihrer angegebenen Version aus dem
	 * SW-Repository geholt.
	 * 
	 * Für dev und binary wird versucht die Resourcen als jar-Dateien zu holen
	 * 
	 * Für war, ear und tgz werden die Dateien mit ihrem Typ als Suffix aus dem
	 * Repository geholt. Also zum Beispiel: 18 snl-runtime snl-runtime 4.2.9
	 * tgz snl component *
	 * 
	 * wird als snl-runtime.tgz angefordert.
	 * 
	 * Wird misc als Typ angegeben, so wird das Modulname gleich dem Dateinamen
	 * gesetzt.
	 * 
	 * Im Anschluss an die Library wird noch versucht die Source-Archive zu
	 * erhalten. Kann dies nicht gefunden werden, so wird eine Warn-Meldung auf
	 * der Kommandozeile ausgegeben.
	 * 
	 * @param moduleList
	 * @throws BuildException
	 */
	private void processResources(ArrayList<Module> moduleList)
			throws BuildException {

		try {

			Module module;
			String moduleType;
			String localPath = "";
			String remotePath;
			URL url;

			Project project = getProject();

			String libraryPath = System.getProperty("java.library.path");
			libraryPath += System.getProperty("path.separator")
					+ "/usr/lib/jni";
			System.setProperty("java.library.path", libraryPath);
			logger.debug("java.library.path=" + libraryPath);

			for (Iterator<Module> iterator = moduleList.iterator(); iterator
					.hasNext();) {

				module = iterator.next();
				// logger.info("Module name=" + module.getName());
				// logger.info("Module arch=" + module.getArch());

				// System.out.println("=== " + module.getLibName() + " ===");

				moduleType = module.getType();
				// System.out.println("moduletype=" + moduleType);

				if (isBinaryType(moduleType)) {

					String libName = module.getLibName();
					String bundleName = module.getBundleName();

					String moduleextension = "";

					remotePath = jarsUrl + module.getLibName() + "/"
							+ module.getVersion() + "/";

					if (moduleType.equals("binary") || moduleType.equals("dev")) {

						moduleextension = ".jar";
						localPath = FileServices.appendFileName(
								project.getProperty("assembly.lib"), libName
										+ BRANCH_DELIM + module.getVersion()
										+ moduleextension);

					} else if (moduleType.equals("war")
							|| moduleType.equals("ear")
							|| moduleType.equals("tgz")) {

						// für war,ear und tgz1 wird der Modultyp als Endung
						// angehängt
						moduleextension = "." + moduleType;
						localPath = FileServices.appendFileName(
								project.getProperty("assembly." + moduleType),
								libName + moduleextension);

					} else if (moduleType.equals("misc")) {

						localPath = FileServices.appendFileName(
								project.getProperty("assembly.misc"), libName);
					} else {

						localPath = FileServices.appendFileName(
								project.getProperty("assembly.misc"), libName);
					}

					// Im Fall misc wird der Modulname direkt aus dem
					// SW-Repository gezogen
					remotePath += libName + moduleextension;

					File localFile = new File(localPath);

					if (localFile.exists() == true) {
						System.out.println(localPath + " [OK]");
						continue;
					}

					url = new URL(remotePath);
					try {

						wget(url, localPath);

					} catch (FileNotFoundException oops) {

						remotePath = jarsUrl + libName + "/"
								+ module.getVersion() + "/" + libName
								+ BRANCH_DELIM + module.getVersion()
								+ moduleextension;

						url = new URL(remotePath);
						wget(url, localPath);
					}

					// Laden des OSGI-Bundles
					remotePath = bundlesUrl + bundleName + "/"
							+ module.getVersion() + "/" + bundleName
							+ BRANCH_DELIM + module.getVersion() + ".jar";

					url = new URL(remotePath);

					localPath = FileServices.appendFileName(
							project.getProperty("assembly.bundles"), bundleName
									+ BRANCH_DELIM + module.getVersion()
									+ ".jar");

					try {

						wget(url, localPath);

					} catch (FileNotFoundException oops) {
						// Kein Bundle verfügbar
					}

					// laden der src-dateien
					remotePath = jarsUrl + libName + "/" + module.getVersion()
							+ "/" + libName + BRANCH_DELIM
							+ module.getVersion() + "-src.zip";

					url = new URL(remotePath);

					localPath = FileServices.appendFileName(
							project.getProperty("assembly.src"), libName + "-"
									+ module.getVersion() + "-src.zip");

					try {
						wget(url, localPath);
					} catch (FileNotFoundException oops) {
						// System.out.println("[WARNING] File not found: "
						// + oops.getMessage());
					}

				} else {

					ScreenLogger screen = new ScreenLogger(new PrintWriter(
							System.out));

					Properties properties = new Properties();
					properties.setProperty("confFile",
							confFile.getAbsolutePath());

					AntRunner runner = new AntRunner(screen,
							module.getBuildFile(), "process-resources",
							new HashMap<String, String>());
					runner.start();

					try {
						runner.join();
					} catch (InterruptedException oops) {
						throw new BuildException("Interrupted!");
					}

					if (runner.failed()) {

						System.err.println(runner.getStackTraceAsString());
						throw new BuildException("Die Generierung des Moduls "
								+ module.getLibName() + " ist fehlgeschlagen!");
					}
				}
			}

		} catch (Exception oops) {
			throw new BuildException(oops);
		}
	}

	private void printLog(File logFile) throws IOException {

		if (logFile == null) {
			throw new IllegalArgumentException(
					"The argument logFile may not be null!");
		}

		BufferedReader reader = new BufferedReader(new FileReader(logFile));

		String line = reader.readLine();

		while (line != null) {

			log(line);
			line = reader.readLine();
		}

		reader.close();
	}

	/**
	 * Überprüft, ob der übergebene Typ einem der binary-Typen entspricht
	 * 
	 * @param moduleType
	 *            der Typ, der überprüft wird
	 * @return true wenn es sich um einen der oben genannten binary-Modul-Typen
	 *         handelt
	 */
	private boolean isBinaryType(String moduleType) {
		return (moduleType.equals("binary") || moduleType.equals("dev")
				|| moduleType.equals("war") || moduleType.equals("ear")
				|| moduleType.equals("tgz") || moduleType.equals("misc"));
	}

	/**
	 * Überprüft über die Validierungen der {@link SokoTask} hinaus den Typ,
	 * welcher den einzelnen Komponenten in der Projektkonfiguration gegeben
	 * wurde.
	 * 
	 * Der Typ muss einem der folgenden Liste entsprechen:
	 * <ul>
	 * <li>component</li>
	 * <li>module</li>
	 * <li>ejb</li>
	 * <li>package</li>
	 * <li>program</li>
	 * <li>process-resources</li>
	 * <li>server</li>
	 * <li>tool</li>
	 * </ul>
	 * 
	 * @see org.dbdoclet.antol.task.SokoTask#validate()
	 */
	@Override
	protected void validate() throws BuildException {

		super.validate();

		if (type == null || type.length() == 0) {
			log("Das Attribut type ist zwingend erforderlich.");
			throw new BuildException("Das Attribut type wurde nicht gesetzt!");
		}

		if (type.equals("*") == false && type.equals("base") == false
				&& type.equals("component") == false
				&& type.equals("module") == false
				&& type.equals("ejb") == false
				&& type.equals("global") == false
				&& type.equals("lib") == false
				&& type.equals("package") == false
				&& type.equals("program") == false
				&& type.equals("process-resources") == false
				&& type.equals("server") == false
				&& type.equals("tool") == false) {

			throw new BuildException("[Assemble] Ungültiges Attribute [" + type
					+ "]!");
		}

		if (targets == null) {
			targets = new Vector<String>();
		}
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public void setTarget(String target) {

		if (target == null) {
			throw new IllegalArgumentException(
					"The argument target must not be null!");
		}

		StringTokenizer stz = new StringTokenizer(target);
		targets = new Vector<String>();

		while (stz.hasMoreTokens()) {
			targets.add(stz.nextToken());
		}
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public void setConf(String conf) {
		this.conf = conf;
	}

	public String getConf() {
		return conf;
	}
}
