/* 
 * $Id$
 *
 * ### Copyright (C) 2005-2007 Michael Fuchs ###
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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.FindServices;

public class SokoTask extends Task {

	class JarFilenameFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {

			name = name.toLowerCase();

			if (name.endsWith(".jar") || name.endsWith(".tld")) {
				return true;
			} else {
				return false;
			}
		}
	}

	class WorkbenchFilenameFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {

			if (name.equals("Werkbank")) {
				return true;
			} else {
				return false;
			}
		}
	}

	class WorkspaceFilenameFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {

			if (name.equals("workspace.xml")) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static String appendFileName(File dir, String filename) {

		if (dir == null) {
			throw new IllegalArgumentException(
					"The argument dir must not be null!");
		}

		return appendFileName(dir.getAbsolutePath(), filename);
	}

	public static String appendFileName(String path1, String filename) {

		if (path1 == null || path1.length() == 0) {
			throw new IllegalArgumentException(
					"The argument path1 must not be null!");
		}

		if (filename == null || filename.length() == 0) {
			throw new IllegalArgumentException(
					"The field filename must not be null!");
		}

		if (path1.endsWith(File.separator) == false) {
			path1 += File.separator;
		}

		if (filename.startsWith(File.separator) == true
				&& filename.length() > 1) {
			filename = filename.substring(1);
		}

		if (filename.endsWith(File.separator) == true) {
			throw new IllegalArgumentException(
					"The filename must not end with '" + File.separator + "'!");
		}

		if (filename.length() == 0) {
			throw new IllegalArgumentException(
					"The filename must not be of length 0!");
		}

		return path1 + filename;
	}

	public static String appendPath(File dir1, File dir2) {

		if (dir1 == null) {
			throw new IllegalArgumentException(
					"The argument dir1 must not be null!");
		}

		if (dir2 == null) {
			throw new IllegalArgumentException(
					"The argument dir2 must not be null!");
		}

		return appendPath(dir1.getPath(), dir2.getPath());
	}

	public static String appendPath(String path1, String path2) {

		if (path1 == null || path1.length() == 0) {
			throw new IllegalArgumentException(
					"The argument path1 must not be null!");
		}

		if (path2 == null || path2.length() == 0) {
			throw new IllegalStateException("The field path2 must not be null!");
		}

		if (path1.endsWith(File.separator) == false) {
			path1 += File.separator;
		}

		if (path2.startsWith(File.separator) == true) {

			if (path2.length() > File.separator.length()) {
				path2 = path2.substring(File.separator.length());
			}
		}

		if (path2.endsWith(File.separator) == false) {
			path2 += File.separator;
		}

		return path1 + path2;
	}

	public static File createPath(File dir) throws IOException {

		if (dir == null) {
			throw new IllegalArgumentException("Parameter dir is null!");
		}

		if (dir.exists() == false) {

			if (dir.mkdirs() == false) {
				throw new IOException("Couldn't create path '" + dir.getPath()
						+ "'.");
			}
		}

		return dir;
	}

	public static File createPath(String path) throws IOException {

		if (path == null) {
			throw new IllegalArgumentException("Parameter path is null!");
		}

		return createPath(new File(path));
	}

	protected File confFile;

	protected String name;
	protected String jarsUrl = "http://svn.unico.com/jars/";
	protected String bundlesUrl = "http://svn.unico.com/bundles/";
	protected String svnUrl = "http://svn.unico.com/svn/";
	private boolean verbose = false;

	/**
	 * Sucht ausgehend vom aktuellen Arbeitsverzeichnis nach dem Verzeichnis der
	 * verwendeten Werkbank. Die Werkbank enthält den Quellcode, alle
	 * Bibliotheken und den Bauplan des Projekts. Das Wurzelverzeichnis wird
	 * enthält die Datei workspace.xml oder trägt den Namen Werkbank. Falls kein
	 * Verzeichnis gefunden werden kann, wird eine
	 * <code>BuildException ausgelöst</code>.
	 * 
	 * @return
	 * @throws BuildException
	 */
	protected File findWorkbenchDirectory() throws BuildException {

		Project project = getProject();
		File dir = project.getBaseDir();

		while (dir != null) {

			if (dir.getName().equals("Werkbank")) {
				break;
			}

			File[] list = dir.listFiles(new FilenameFilter() {

				public boolean accept(File dir, String name) {

					if (name.equals("workspace.xml")) {
						return true;
					}

					return false;
				}

			});

			if (list != null && list.length > 0) {
				break;
			}

			dir = dir.getParentFile();
		}

		if (dir == null) {
			throw new BuildException(
					"Das Verzeichnis Werkbank konnte nicht gefunden werden!");
		}

		return dir;
	}

	protected File findWorkspaceDirectory() throws BuildException {

		File[] list;
		String path;

		Project project = getProject();

		File baseDir = project.getBaseDir();
		File dir = baseDir;

		WorkspaceFilenameFilter filter = new WorkspaceFilenameFilter();
		list = dir.listFiles(filter);

		while (list.length == 0) {

			dir = dir.getParentFile();

			if (dir == null) {

				path = FileServices.appendPath(baseDir, "work");
				return new File(path);
			}

			list = dir.listFiles(filter);
		}

		return dir;
	}

	protected String getConfFileName() {

		File workspaceDir = findWorkspaceDirectory();
		String path = FileServices.appendPath(workspaceDir, "projects");
		File projectsDir = new File(path);

		ArrayList<File> confFileList = new ArrayList<File>();
		FindServices.findFile(projectsDir, ".*\\.conf", confFileList);

		if (confFileList.size() == 0) {
			return null;
		}

		File confFile = confFileList.get(0);
		return confFile.getAbsolutePath();
	}

	protected File getDevelopmentDirectory() {

		File workbenchDirectory = findWorkbenchDirectory();
		String path = FileServices
				.appendPath(workbenchDirectory, "development");
		return new File(path);
	}

	protected HashMap<String, String> getDevelopmentJarMap() throws IOException {

		HashMap<String, String> map = new HashMap<String, String>();

		ArrayList<Module> moduleList = getModuleList();
		Iterator<Module> moduleIterator = moduleList.iterator();

		File developmentJarsDirectory = getDevelopmentJarsDirectory();

		while (moduleIterator.hasNext()) {

			Module module = moduleIterator.next();

			if (module.getType().equals("dev") == true) {

				String path = FileServices.appendPath(developmentJarsDirectory,
						module.getLibName());

				if (isVerbose()) {
					log("Durchsuche Entwicklungsverzeichnis " + path + "...");
				}

				File moduleDir = new File(path);

				File[] jarFileList = moduleDir
						.listFiles(new JarFilenameFilter());

				if (jarFileList != null) {
					for (int i = 0; i < jarFileList.length; i++) {
						// log(jarFileList[i].getName());
						map.put(jarFileList[i].getName(),
								jarFileList[i].getAbsolutePath());
					}
				}
			}
		}

		return map;
	}

	protected File getDevelopmentJarsDirectory() {

		File developmentDirectory = getDevelopmentDirectory();
		String path = FileServices.appendPath(developmentDirectory, "jars");
		return new File(path);
	}

	protected ArrayList<Module> getModuleList() throws IOException {

		if (confFile == null) {
			return new ArrayList<Module>();
		}

		Project project = getProject();

		ArrayList<Module> moduleList = new ArrayList<Module>();
		Module module;

		BufferedReader reader = new BufferedReader(new FileReader(confFile));

		StringTokenizer stz;
		String[] tokens;

		String line;
		int counter = 0;
		int index;

		line = reader.readLine();
		counter++;

		while (line != null) {

			module = new Module(project);

			if (line.trim().length() == 0) {

				line = reader.readLine();
				counter++;
				continue;
			}

			if (line.trim().startsWith("#")) {

				index = line.indexOf("#");
				if (index == line.length() - 1) {
					module.addText("\n");
				} else {
					module.addText(line.substring(index + 1).trim());
				}

				line = reader.readLine();
				counter++;
				continue;
			}

			stz = new StringTokenizer(line);

			if (stz.countTokens() != 8) {

				throw new BuildException("Syntaxfehler in Zeile " + counter
						+ ": Die Zeile enthält " + stz.countTokens()
						+ " Spalten statt der geforderten 8.");
			}

			tokens = new String[8];
			index = 0;

			while (stz.hasMoreTokens()) {
				tokens[index++] = stz.nextToken();
			}

			int col = 0;
			module.setPos(Integer.parseInt(tokens[col++]));
			module.setBundleName(tokens[col++]);
			module.setLibName(tokens[col++]);
			module.setVersion(tokens[col++]);
			module.setType(tokens[col++]);
			module.setSubsystem(tokens[col++]);
			module.setLevel(tokens[col++]);
			module.setPackageName(tokens[col++]);

			moduleList.add(module);
			line = reader.readLine();
			counter++;
		}

		reader.close();

		return moduleList;
	}

	public String getName() {
		return name;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	protected void validate() throws BuildException {

		String confFileName = getConfFileName();

		if (isVerbose()) {
			log("Konfigurationsdatei: " + confFileName);
		}

		if (confFileName == null || confFileName.length() == 0) {
			return;
		}

		confFile = new File(confFileName);

		if (confFile.exists() == false) {
			throw new BuildException("Die Konfigurationsdatei "
					+ confFile.getAbsolutePath() + " wurde nicht gefunden!");
		}

		if (confFile.canRead() == false) {
			throw new BuildException("Die Konfigurationsdatei "
					+ confFile.getAbsolutePath()
					+ " kann nicht zum Lesen geöffnet werden!");
		}
	}

	protected void wget(URL url, String destFileName)
			throws FileNotFoundException {

		InputStream instr = null;
		BufferedOutputStream outstr = null;

		try {

			File file = new File(destFileName);
			File dir = file.getParentFile();

			if (dir != null && dir.exists() == false) {
				FileServices.createPath(dir);
			}

			URLConnection conn = url.openConnection();
			instr = conn.getInputStream();
			outstr = new BufferedOutputStream(
					new FileOutputStream(destFileName));

			byte[] buffer = new byte[4096];
			int nread;
			long nwritten = 0;

			System.out.println(url.toString() + " -> " + destFileName);
			// System.out.println("  -> " + destFileName);

			while ((nread = instr.read(buffer)) != -1) {
				outstr.write(buffer, 0, nread);
				nwritten += nread;
			}

		} catch (FileNotFoundException oops) {

			throw oops;

		} catch (Exception oops) {

			// logger.fatal("process-resources", oops);
			log("wget FAILED");

		} finally {

			try {

				if (instr != null) {
					instr.close();
				}

				if (outstr != null) {
					outstr.close();
				}

			} catch (IOException ioe) {
				// logger.fatal("process-resources", ioe);
			}
		}
	}

}
