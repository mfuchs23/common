package org.dbdoclet.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.CreatePathException;
import org.dbdoclet.DeleteFileException;
import org.dbdoclet.FileAccessDeniedException;
import org.dbdoclet.RenameFileException;
import org.dbdoclet.ServiceException;
import org.dbdoclet.Sfv;
import org.dbdoclet.io.DirectoryFilter;
import org.dbdoclet.io.EndsWithFilter;
import org.dbdoclet.io.MimeType;
import org.dbdoclet.io.StartsWithFilter;
import org.dbdoclet.progress.ProgressEvent;
import org.dbdoclet.progress.ProgressListener;
import org.dbdoclet.template.TemplateTransformException;
import org.dbdoclet.template.TemplateTransformer;

/**
 * Die Klasse <code>FileServices</code> stellt statische Methoden zur Verfügung, die
 * Dateien und Dateverzeichnisse manipulieren und auswerten bereit.
 * 
 * @author <a href="mailto:michael.fuchs@dbdoclet.org">Michael Fuchs</a>
 * @version 8.0
 */
public class FileServices {

	/** Alle Dateien */
	public static final int ALL = 2;

	/** Verwende den EndsWith-Filter */
	public static final int ENDS_WITH = 1;

	public static final String FSEP = System.getProperty("file.separator");

	private static Log logger = LogFactory.getLog(FileServices.class);

	/** Verwende den StartsWith-Filter */
	public static final int STARTS_WITH = 0;

	public static final Pattern PATTERN_ABSOLUTE_WINDOWS_PATH = Pattern
			.compile("^([a-z,A-Z]:|/)/.*$");

	/**
	 * @see #appendFileName(String dir, String filename)
	 */
	public static String appendFileName(File dir, String filename) {

		if (dir == null) {
			throw new IllegalArgumentException(
					"The argument dir must not be null!");
		}

		return appendFileName(dir.getAbsolutePath(), filename);
	}

	/**
	 * Die Methode <code>appendFileName</code> hängt den Dateinamen and das
	 * Verzeichnis an.
	 * 
	 * Falls der Dateinamen mit dem betriebsspezifischen Pfadtrennzeichen endet
	 * wird eine <code>IllegalArgumentException</code> ausgelöst.
	 * 
	 * <div id="example_FileServices_appendFileName_1" title="Beispiel:">
	 * 
	 * <pre>
	 * 
	 * String path = System.getProperty(&quot;user.home&quot;);
	 * 
	 * path = FileServices.appendPath(path, &quot;logs&quot;);
	 * FileServices.createPath(path);
	 * 
	 * path = FileServices.appendFileName(path, &quot;Errors.log&quot;);
	 * File file = new File(path);
	 * 
	 * </pre>
	 * 
	 * </div>
	 * 
	 * 
	 */
	public static String appendFileName(String path1, String filename) {

		if (path1 == null || path1.length() == 0) {
			throw new IllegalArgumentException(
					"The argument path1 must not be null!");
		}

		if (filename == null) {
			throw new IllegalArgumentException(
					"The argument filename must not be null!");
		}

		if (filename.trim().length() == 0) {
			throw new IllegalArgumentException(
					"The argument filename must not be empty!");
		}

		if (path1.endsWith(File.separator) == false) {
			path1 += File.separator;
		}

		if (filename.startsWith(File.separator) == true
				&& filename.length() > 1) {
			filename = filename.substring(1);
		}

		if (filename.endsWith(File.separator) == true) {
			throw new IllegalArgumentException("The name of file '" + filename
					+ "' must not end with '" + File.separator + "'!");
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

	public static String appendPath(File dir1, String path2) {

		if (dir1 == null) {
			throw new IllegalArgumentException(
					"The argument dir1 must not be null!");
		}

		if (path2 == null) {
			throw new IllegalArgumentException(
					"The argument path2 must not be null!");
		}

		return appendPath(dir1.getPath(), path2);
	}

	/**
	 * Die Methode <code>appendPath</code> hängt das Verzeichnis path2 an das
	 * Verzeichnis path1.
	 * 
	 * Der erstellte Verzeichnispfad endet in jedem Fall mit dem
	 * betriebsspezifischen Trennzeichen für Verzeichnisse.
	 */
	public static String appendPath(String path1, String path2) {

		if (path1 == null || path1.length() == 0) {
			throw new IllegalArgumentException(
					"The argument path1 must not be null!");
		}

		if (path2 == null || path2.length() == 0) {
			throw new IllegalStateException("The field path2 must not be null!");
		}

		path1 = path1.replace("/", File.separator);
		path2 = path2.replace("/", File.separator);

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

	/**
	 * Die Methode <code>cleanRelatedFiles</code> löscht eine Datei oder ein
	 * Verzeichnis und alle "verwandten" Dateien und Verzeichnisse.
	 * 
	 * Als verwandte Dateiobjekte werden alle Dateien und Verzeichnisse
	 * betrachtet, deren Dateinname aus dem Dateinamen der Referenzdatei mit
	 * angehängtem Bindestrich und einer Ziffer besteht. Falls eine
	 * Dateinamenserweiterung vorhanden ist, wird diese am Ende des Dateinamens
	 * erwartet, z.B. <code>Addressen-12.txt</code> ist verwand mit
	 * <code>Addressen.txt</code>.
	 * 
	 * @param file
	 *            <code>File</code>
	 * @exception IOException
	 */
	public static void cleanRelatedFiles(File file) throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		int index = 1;
		String fileBase;
		String fileExt;
		String path;

		fileBase = getFileBase(file.getCanonicalPath());
		fileExt = getExtension(file.getCanonicalPath());

		logger.debug("Verzeichnis aufräumen: " + file.getCanonicalPath());

		while (file.exists() == true) {

			try {
				setWritable(file);
				delete(file);
			} catch (Exception oops) {
				logger.fatal("FileServices.cleanRelatedFiles", oops);
			}

			path = fileBase + "-" + String.valueOf(index++);

			if (fileExt != null && fileExt.trim().length() > 0) {
				path += "." + fileExt;
			}

			file = new File(path);
			logger.debug("Verzeichnis aufräumen: " + file.getCanonicalPath());
		}
	}

	public static boolean contains(File file, String regex)
			throws ServiceException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		if (regex == null) {
			throw new IllegalArgumentException(
					"The argument regex must not be null!");
		}

		BufferedReader reader = null;

		try {

			Pattern pattern = Pattern.compile(regex);
			Matcher matcher;

			reader = new BufferedReader(new FileReader(file));

			String line;

			while ((line = reader.readLine()) != null) {

				matcher = pattern.matcher(line);
				if (matcher.find()) {
					return true;
				}
			}

			return false;

		} catch (IOException oops) {

			throw new ServiceException(oops);

		} finally {

			try {

				if (reader != null) {
					reader.close();
				}

			} catch (IOException ioe) {

				ioe.printStackTrace();
			}
		}
	}

	public static boolean copyDir(File src, File dest) throws IOException {

		if (src == null) {
			throw new IllegalArgumentException(
					"The argument src must not be null!");
		}

		if (dest == null) {
			throw new IllegalArgumentException(
					"The argument dest must not be null!");
		}

		return copyDir(src.getCanonicalPath(), dest.getCanonicalPath(), null,
				null);
	}

	public static boolean copyDir(File src, File dest,
			Map<String, String> filterSet) throws IOException {

		if (src == null) {
			throw new IllegalArgumentException(
					"The argument src must not be null!");
		}

		if (dest == null) {
			throw new IllegalArgumentException(
					"The argument dest must not be null!");
		}

		return copyDir(src.getCanonicalPath(), dest.getCanonicalPath(), null,
				filterSet, null, null);
	}

	public static boolean copyDir(File src, File dest, Pattern includePattern)
			throws IOException {

		if (src == null) {
			throw new IllegalArgumentException(
					"The argument src must not be null!");
		}

		if (dest == null) {
			throw new IllegalArgumentException(
					"The argument dest must not be null!");
		}

		if (includePattern == null) {
			throw new IllegalArgumentException(
					"The argument includePattern must not be null!");
		}

		Pattern[] patterns = new Pattern[1];
		patterns[0] = includePattern;

		return copyDir(src.getPath(), dest.getPath(), patterns, null, null,
				null);
	}

	public static boolean copyDir(File src, String dest, int index)
			throws IOException {

		if (src == null) {
			throw new IllegalArgumentException(
					"The argument src must not be null!");
		}

		return copyDir(src.getCanonicalPath(), dest, index);
	}

	public static boolean copyDir(File src, String dest, int index,
			ProgressListener listener, String resourceString)
			throws IOException {

		if (src == null) {
			throw new IllegalArgumentException(
					"The argument src must not be null!");
		}

		return copyDir(src.getCanonicalPath(), dest, index, listener,
				resourceString);
	}

	public static boolean copyDir(String src, String dest) throws IOException {

		return copyDir(src, dest, null, null, null, null);
	}

	public static boolean copyDir(String src, String dest, int index)
			throws IOException {

		return copyDir(src, dest, index, null, null);
	}

	/**
	 * Die Methode <code>copyDir</code> kopiert das Verzeichnis mit einem Teil
	 * seines übergeordneten Pfades in das angegebene Zielverzeichnis.
	 * 
	 * @param src
	 *            <code>String</code>
	 * @param dest
	 *            <code>String</code>
	 * @param index
	 *            <code>int</code>
	 * @exception IOException
	 */
	public static boolean copyDir(String src, String dest, int index,
			ProgressListener listener, String resourceString)
			throws IOException {

		String path = normalizePath(src);
		path = StringServices.chop(path, "/");

		String name = getPathToken(path, index);

		if (name == null) {
			throw new FileNotFoundException(src);
		}

		for (int i = index; i > 0; i--) {

			name = getPathToken(path, i);
			dest = appendPath(dest, name);

			logger.debug("Anlegen des Verzeichnisses " + dest + ".");

			dest = normalizePath(dest);
			FileServices.createPath(dest);
		}

		return copyDir(src, dest, null, null, listener, resourceString);
	}

	public static boolean copyDir(String src, String dest,
			Map<String, String> filterSet) throws IOException {

		return copyDir(src, dest, null, filterSet, null, null);
	}

	public static boolean copyDir(String src, String dest,
			Pattern[] includePatterns) throws IOException {

		return copyDir(src, dest, includePatterns, null, null, null);
	}

	/**
	 * Die Methode <code>copyDir</code> kopiert ein Verzeichnis und seinen
	 * Inhalt einschließlich aller Unterverzeichnissen.
	 * 
	 * @param src
	 *            Der Parameter <code>src</code> enthält das Quellverzeichnis,
	 *            welches kopiert werden soll. Wird als Wert <code>null</code>
	 *            übergeben existiert das Verzeichnis nicht oder der angegebene
	 *            Pfad ist kein Verzeichnis, wird eine
	 *            <code>IllegalArgumentException</code> ausglöst.
	 * 
	 * @param dest
	 *            Der Parameter <code>dest</code> enthält das Zielverzeichnis in
	 *            das kopiert werden soll. Wird als Wert <code>null</code>
	 *            übergeben oder ist der angegebene Pfad kein Verzeichnis, wird
	 *            eine <code>IllegalArgumentException</code> ausglöst. Falls das
	 *            Zielverzeichnis nicht existiert wird es angelegt.
	 * 
	 * @exception IOException
	 *                Falls beim Kopieren ein Ein/Ausgabefehler auftritt.
	 */
	public static boolean copyDir(String src, String dest,
			Pattern[] includePatterns, Map<String, String> filterSet,
			ProgressListener listener, String resourceString)
			throws IOException {

		String msg;

		src = normalizePath(src);
		dest = normalizePath(dest);

		logger.debug(src + " -> " + dest);

		if (src == null) {
			throw new IllegalArgumentException("Parameter src is null!");
		}

		if (dest == null) {
			throw new IllegalArgumentException("Parameter dest is null!");
		}

		File srcDir = new File(src);

		if (srcDir.exists() == false) {
			throw new FileNotFoundException("Source path '"
					+ srcDir.getAbsolutePath() + "' dosen't exist!");
		}

		if (srcDir.isDirectory() == false) {
			throw new IllegalArgumentException("Source path '"
					+ srcDir.getAbsolutePath() + "' is not a directory!");
		}

		File destDir = FileServices.createPath(dest);

		if (destDir.isDirectory() == false) {
			throw new IllegalArgumentException("Destination path '"
					+ destDir.getAbsolutePath() + "' is not a directory!");
		}

		src = normalizePath(srcDir.getCanonicalPath());
		dest = normalizePath(destDir.getCanonicalPath());

		File[] files = srcDir.listFiles();
		File destFile;
		String destFileName;

		for (int i = 0; i < files.length; i++) {

			destFileName = appendFileName(dest, files[i].getName());
			destFile = new File(destFileName);

			if (files[i].isDirectory()) {

				String newSrc = files[i].getCanonicalPath();
				newSrc = normalizePath(newSrc);

				String newDest = StringServices.replace(newSrc, src, dest);

				copyDir(newSrc, newDest, includePatterns, filterSet, listener,
						resourceString);

				if (includePatterns != null) {

					File newDestDir = new File(newDest);

					if (newDestDir.list().length == 0) {
						delete(newDestDir);
					}
				}
			}

			if (includePatterns != null) {

				boolean doCopy = false;

				for (int p = 0; p < includePatterns.length; p++) {

					Matcher matcher = includePatterns[p].matcher(destFileName);

					if (matcher.matches() == true) {
						doCopy = true;
						break;
					}
				}

				if (doCopy == false) {
					continue;
				}
			}

			if (files[i].isFile()) {

				if (listener != null) {

					logger.debug("Firing progress event.");

					if (resourceString != null) {
						msg = MessageFormat.format(resourceString,
								files[i].getPath(), destFile.getPath());
					} else {
						msg = files[i].getName();
					}

					if (listener.progress(new ProgressEvent(msg, false)) == false) {
						return false;
					}
				}

				logger.debug(files[i].getAbsolutePath() + " -> "
						+ destFile.getAbsolutePath());

				if (filterSet == null || isBinary(files[i])) {

					FileInputStream in;
					FileOutputStream out;

					in = new FileInputStream(files[i]);
					out = new FileOutputStream(destFile);

					int n = 0;
					byte[] buffer = new byte[4096];
					while ((n = in.read(buffer, 0, 4096)) != -1) {
						out.write(buffer, 0, n);
					}

					in.close();

					out.flush();
					out.close();

					if (destFile.exists() == false) {
						throw new IOException("Couldn't create file "
								+ destFileName + "!");
					}

					if (destFile.length() != files[i].length()) {
						throw new IOException("Files differ after copy: "
								+ destFileName + "!");
					}

				} else {

					try {

						TemplateTransformer trafo = TemplateTransformer
								.newInstance(files[i]);
						trafo.transform(filterSet, destFile);

					} catch (TemplateTransformException oops) {

						IOException ioe = new IOException(
								"TemplateTransformException: "
										+ oops.getMessage());
						ioe.initCause(oops);
						ioe.fillInStackTrace();
						throw ioe;
					}

					if (destFile.exists() == false) {
						throw new IOException("Couldn't create file "
								+ destFileName + "!");
					}
				}
			}
		}

		return true;
	}

	public static boolean copyDir(String src, String dest,
			ProgressListener listener, String resourceString)
			throws IOException {

		return copyDir(src, dest, null, null, listener, resourceString);
	}

	public static void copyFileToDir(File src, File destdir) throws IOException {

		copyFileToDir(src.getPath(), destdir.getPath());
	}

	/**
	 * Die Methode <code>copyFileToDir</code> kopiert eine einzelne Datei in das
	 * angegebene Zielverzeichnis.
	 * 
	 * Falls das Zielverzeichnis nicht existiert, wird es angelegt.
	 * 
	 * Die Datei wird nur kopiert, falls die Quelldatei neuer als die Zieldatei
	 * ist.
	 * 
	 * @exception IOException
	 *                falls ein Fehler beim Kopieren auftritt.
	 */
	public static void copyFileToDir(String src, String destdir)
			throws IOException {

		File file;

		if (src == null) {
			throw new IllegalArgumentException("Parameter src is null!");
		}

		file = new File(src);

		if (file.exists() == false) {
			throw new IllegalArgumentException("Parameter src ("
					+ file.getAbsolutePath() + ") can not be found!");
		}

		if (file.isFile() == false) {
			throw new IllegalArgumentException("Parameter src ("
					+ file.getAbsolutePath() + ") is not a normal file!");
		}

		if (destdir == null) {
			throw new IllegalArgumentException("Parameter destdir is null!");
		}

		file = new File(destdir);

		if (file.exists() && file.isDirectory() == false) {
			throw new IllegalArgumentException("Parameter destdir ("
					+ file.getAbsolutePath() + ") is not a directory!");
		}

		File srcFile = new File(src);

		String path = appendFileName(destdir, srcFile.getName());
		File destFile = new File(path);

		FileInputStream in;
		FileOutputStream out;

		in = new FileInputStream(srcFile);
		out = new FileOutputStream(destFile);

		int n = 0;
		byte[] buffer = new byte[4096];
		while ((n = in.read(buffer, 0, 4096)) != -1) {
			out.write(buffer, 0, n);
		}

		in.close();
		out.close();
	}

	/**
	 * Die Methode <code>copyFileToFile</code> kopiert eine einzelne Datei,
	 * falls die Quelldatei neuer als die Zieldatei ist.
	 * 
	 * Falls das Zielverzeichnis nicht existiert, wird es angelegt.
	 * 
	 * @throws IOException
	 *             falls ein Fehler beim Kopieren auftritt.
	 */
	public static void copyFileToFile(File src, File dest) throws IOException {

		copyFileToFile(src.getAbsolutePath(), dest.getAbsolutePath(), null);
	}

	public static void copyFileToFile(File srcFile, File destFile,
			Map<String, String> filterSet) throws IOException {

		if (srcFile == null) {
			throw new IllegalArgumentException("Parameter srcFile is null!");
		}

		if (srcFile.exists() == false) {
			throw new IllegalArgumentException(
					"Parameter srcFile doesn't exist! '"
							+ srcFile.getAbsolutePath() + "'");
		}

		if (srcFile.isFile() == false) {
			throw new IllegalArgumentException(
					"Parameter srcFile is not a normal file! '"
							+ srcFile.getAbsolutePath() + "'");
		}

		if (destFile == null) {
			throw new IllegalArgumentException("Parameter destFile is null!");
		}

		if (srcFile.exists() && srcFile.isFile() == false) {
			throw new IllegalArgumentException(
					"Parameter destFile is not a normal file! '"
							+ srcFile.getAbsolutePath() + "'");
		}

		if (srcFile.equals(destFile)) {
			return;
		}

		if (destFile.exists() == true && destFile.canWrite() == false) {
			throw new FileAccessDeniedException(destFile);
		}

		String srcPath = srcFile.getCanonicalPath();
		srcPath = normalizePath(srcPath);

		String destPath = destFile.getCanonicalPath();
		destPath = normalizePath(destPath);

		if (destPath.equals(srcPath)) {
			logger.warn("Copy: Files " + srcPath + " and " + destPath
					+ " are identical!");
			return;
		}

		String destParentDir = destFile.getParent();
		if (destParentDir != null && destParentDir.length() > 0) {
			createPath(destParentDir);
		}

		if (filterSet == null || isBinary(srcFile)) {

			FileInputStream in;
			FileOutputStream out;

			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);

			int n = 0;
			byte[] buffer = new byte[4096];

			while ((n = in.read(buffer, 0, 4096)) != -1) {
				out.write(buffer, 0, n);
			}

			in.close();

			out.flush();
			out.close();

		} else {

			try {

				TemplateTransformer trafo = TemplateTransformer
						.newInstance(srcFile);
				trafo.transform(filterSet, destFile);

			} catch (TemplateTransformException oops) {

				IOException ioe = new IOException(
						"TemplateTransformException: " + oops.getMessage());
				ioe.initCause(oops);
				ioe.fillInStackTrace();
				throw ioe;
			}
		}
	}

	public static void copyFileToFile(String src, String dest)
			throws IOException {

		copyFileToFile(new File(src), new File(dest), null);
	}

	/**
	 * Die Methode <code>copyFileToFile</code> kopiert eine einzelne Datei.
	 * Falls das Zielverzeichnis nicht existiert, wird es angelegt.
	 * 
	 * @throws IOException
	 *             falls ein Fehler beim Kopieren auftritt.
	 */
	public static void copyFileToFile(String src, String dest,
			Map<String, String> filterSet) throws IOException {

		copyFileToFile(new File(src), new File(dest), filterSet);
	}

	public static File createParentDir(File dir) throws IOException {

		if (dir == null) {
			throw new IllegalArgumentException(
					"The argument dir must not be null!");
		}

		File parentDir = dir.getParentFile();

		if (parentDir == null) {
			return null;
		}

		return createPath(parentDir);
	}

	/**
	 * Legt ein neues Verzeichnis an.
	 * 
	 * Falls das Verzeichnis bereits existiert, wird dieses ohne Fehlermeldung
	 * zurückgeliefert. Existiert das Verzeichnis nicht und das Anlegen schlägt
	 * fehl wird eine Ausnahme erzeugt.
	 * 
	 * <b>Vorbedingung:</b> Wird als <code>dir</code>-Parameter
	 * <code>null</code> Übergeben, so wird eine
	 * <code>IllegalArgumentException</code> ausgelöst.
	 * 
	 * @exception IOException
	 *                falls das Anlegen des Verzeichnisses fehlschlägt.
	 */
	public static File createPath(File dir) throws IOException {

		if (dir == null) {
			throw new IllegalArgumentException(
					"The argument dir must not be null!");
		}

		String dirName = dir.getCanonicalPath();

		if (JvmServices.isWindows()) {

			if (dirName != null && dirName.length() > 255) {
				throw new CreatePathException(dirName,
						CreatePathException.PATH_TOO_LONG);
			}
		}

		if (dir.exists() == false) {

			if (dir.mkdirs() == false && dir.exists() == false) {

				ArrayList<File> pathList = new ArrayList<File>();
				pathList.add(0, dir);

				File parent = dir.getParentFile();
				File next = dir;

				while (parent != null && parent.exists() == false) {

					pathList.add(0, parent);
					next = parent;

					parent = parent.getParentFile();
				}

				if (parent != null && parent.exists() == true) {

					if (parent.isDirectory() == false) {
						throw new CreatePathException(next.getCanonicalPath(),
								CreatePathException.FILE_PARENT);
					}

					if (parent.canWrite() == false || parent.canRead() == false) {
						throw new CreatePathException(next.getCanonicalPath(),
								CreatePathException.PERMISSION_DENIED);
					}
				}

				throw new CreatePathException(dir.getCanonicalPath());
			}
		}

		return dir;
	}

	/**
	 * Legt ein neues Verzeichnis an.
	 * 
	 * Falls das Verzeichnis bereits existiert, wird dieses ohne Fehlermeldung
	 * zurückgeliefert. Existiert das Verzeichnis nicht und das Anlegen schlägt
	 * fehl wird eine Ausnahme erzeugt.
	 * 
	 * <b>Vorbedingung:</b>Wird als <code>path</code>-Parameter
	 * <code>null</code> übergeben, so wird eine
	 * <code>IllegalArgumentException</code> ausgelöst.
	 * 
	 * @throws IOException
	 *             falls das Anlegen des Verzeichnisses fehlschlägt.
	 */
	public static File createPath(String path) throws IOException {

		if (path == null) {
			throw new IllegalArgumentException("Parameter path is null!");
		}

		return createPath(new File(path));
	}

	public static File createUniqueFile(File file) throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		int index = 1;
		String fileBase;
		String fileExt;
		String path;

		fileBase = getFileBase(file.getCanonicalPath());
		fileExt = getExtension(file.getCanonicalPath());

		while (file.exists() == true) {

			path = fileBase + "-" + String.valueOf(index++);

			if (fileExt != null && fileExt.trim().length() > 0) {
				path += "." + fileExt;
			}

			file = new File(path);
		}

		return file;
	}

	public static String cutPrefixPathItems(String path, int length) {

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path must not be null!");
		}

		if (length < 0) {
			throw new IllegalArgumentException(
					"The argument length must not be < 0!");
		}

		String buffer = normalizePath(path);
		buffer = StringServices.cutPrefix(buffer, "/");

		while (buffer.endsWith("/")) {
			buffer = StringServices.cutSuffix(buffer, "/");
		}

		int index = buffer.indexOf("/");
		int counter = 1;

		while (index > 0 && counter < length) {

			index = buffer.indexOf("/", index + 1);
			counter++;
		}

		return buffer.substring(index + 1);
	}

	public static int delete(File file) throws IOException {

		if (file == null) {
			return 0;
		}

		return delete(file.getPath(), null);
	}

	public static int delete(File file, ProgressListener listener)
			throws IOException {

		return delete(file.getPath(), listener, null);
	}

	/**
	 * Die Methode <code>delete</code> löscht alle regulären Dateien aus dem
	 * Verzeichnis, die auf den regulären Asudruck passen.
	 * 
	 * @param dir
	 *            a <code>File</code> value
	 * @param regexp
	 *            a <code>String</code> value
	 * @return an <code>int</code> value
	 * @exception IOException
	 *                if an error occurs
	 */
	public static int delete(File dir, String regexp) throws IOException {

		if (dir == null) {
			return 0;
		}

		ArrayList<File> fileList = new ArrayList<File>();
		FindServices.findFile(dir, regexp, false, fileList);

		for (File file : fileList) {
			delete(file);
		}

		return fileList.size();
	}

	/**
	 * Die Methode <code>delete</code> löscht eine Datei oder ein Verzeichnis
	 * und alle darin enthaltenen Dateien und Unterverzeichnisse.
	 */
	public static int delete(String path) throws IOException {

		return delete(path, null);
	}

	public static int delete(String path, ProgressListener listener)
			throws IOException {

		return delete(path, listener, null);
	}

	/**
	 * Die Methode <code>delete</code> löscht eine Datei oder ein Verzeichnis
	 * und alle darin enthaltenen Dateien und Unterverzeichnisse.
	 */
	public static int delete(String path, ProgressListener listener,
			String resourceString) throws IOException {
		return delete(new File(path), listener, resourceString);
	}

	/**
	 * Die Methode <code>delete</code> <b>löscht</b> eine Datei oder ein
	 * Verzeichnis und alle darin enthaltenen Dateien und Unterverzeichnisse.
	 */
	public static int delete(File node, ProgressListener listener,
			String resourceString) throws IOException {

		String msg;

		logger.debug("Lösche Pfad " + node + ".");

		if (node == null) {
			return 0;
		}

		boolean rc;
		int counter = 0;

		if (node.exists() == false) {

			logger.debug("Der Pfad " + node.getAbsolutePath()
					+ " existiert nicht!");
			return 0;
		}

		if (node.isFile() == true) {

			rc = node.delete();

			if (rc == false) {

				throw new DeleteFileException(node);

				/*
				 * if (JvmServices.isWindows()) {
				 * 
				 * String[] cmd = new String[4]; cmd[0] = "del"; cmd[1] = "/F";
				 * cmd[2] = "/Q"; cmd[3] = node.getCanonicalPath();
				 * 
				 * ExecResult result = ExecServices.exec(cmd);
				 * 
				 * if (result.getFailed()) { throw new
				 * DeleteFileException(node); }
				 * 
				 * } else if (JvmServices.isUnix()) {
				 * 
				 * String[] cmd = new String[3]; cmd[0] = "rm"; cmd[1] = "-f";
				 * cmd[2] = node.getCanonicalPath();
				 * 
				 * ExecResult result = ExecServices.exec(cmd);
				 * 
				 * if (result.getFailed()) { throw new
				 * DeleteFileException(node); }
				 * 
				 * } else {
				 * 
				 * }
				 */
			}
		}

		if (node.isDirectory() == true) {

			File[] list = node.listFiles();

			if (list != null) {

				for (int i = 0; i < list.length; i++) {

					if (list[i].isDirectory()) {

						counter += delete(list[i].getAbsolutePath(), listener,
								resourceString);

					} else {

						rc = list[i].delete();
						if (rc == false) {
							throw new DeleteFileException(node);
						}
					}
				}
			}

			counter++;

			if (listener != null) {

				logger.debug("Firing progress event.");

				if (resourceString != null) {
					msg = MessageFormat.format(resourceString, node.getPath());
				} else {
					msg = node.getName();
				}

				listener.progress(new ProgressEvent(msg, false));
			}

			rc = node.delete();
			if (rc == false) {
				throw new DeleteFileException(node);
			}
		}

		if (node.exists() == true) {
			throw new DeleteFileException(node);
		}

		return counter;
	}

	/**
	 * Die Methode <code>delete</code> löscht alle Dateien aus dem Verzeichnis
	 * <code>path</code> die auf den Filter <code>filter</code> passen.
	 * 
	 * Die Art des Filters wird durch den Filtertyp bestimmt. Der Typ kann den
	 * Anfang oder das Ende eines Dateinamens überpüfen.
	 * 
	 * Folgende Type können verwendet werden:
	 * <ul>
	 * <li>STARTS_WITH - Testet den Anfang des Dateinamens</li>
	 * <li>ENDS_WITH - Testet das Ende des Dateinamens</li>
	 * <li>ALL - Trifft auf alle Dateien zu</li>
	 * </ul>
	 */
	public static void delete(String path, String filter, int type) {

		if (path == null) {
			return;
		}

		File dir = new File(path);

		if (dir.exists() == false) {
			return;
		}

		File[] list;

		switch (type) {

		case STARTS_WITH:
			list = dir.listFiles(new StartsWithFilter(filter));
			break;

		case ENDS_WITH:
			list = dir.listFiles(new EndsWithFilter(filter));
			break;

		case ALL:
			list = dir.listFiles();
			break;

		default:
			return;

		}

		for (int i = 0; i < list.length; i++) {
			list[i].delete();
		}
	}

	public static void deleteEmptyDirs(File dir) throws IOException {

		if (dir == null) {
			throw new IllegalArgumentException(
					"The argument dir must not be null!");
		}

		File[] list = dir.listFiles();

		for (int i = 0; i < list.length; i++) {

			if (list[i].isDirectory()) {

				deleteEmptyDirs(list[i]);

				if (list[i].list().length == 0) {
					delete(list[i]);
				}
			}
		}
	}

	public static void deleteEmptyDirs(String path) throws IOException {

		deleteEmptyDirs(new File(path));
	}

	/**
	 * Die Methode <code>getAbsoluteDirName</code> liefert das übergeordnete
	 * Verzeichnis des angegebenen Pfades als absoluten Pfad.
	 */
	public static String getAbsoluteDirName(String filename) {

		if (filename == null) {
			throw new IllegalArgumentException("Parameter filename is null!");
		}

		if (filename.length() == 0) {
			throw new IllegalArgumentException("Parameter filename is null!");
		}

		File file = new File(filename);
		String path = file.getAbsolutePath();

		int index = path.lastIndexOf(File.separator);

		if (index == -1) {
			return ".";
		}

		path = path.substring(0, index);

		return path;

	}

	/**
	 * Die Methode <code>getDirName</code> liefert das übergeordnete Verzeichnis
	 * des angegebenen Pfades.
	 */
	public static String getDirName(String fileName) {

		if (fileName == null) {
			throw new IllegalArgumentException("Parameter fileName is null!");
		}

		if (fileName.length() == 0) {
			throw new IllegalArgumentException("Parameter fileName is null!");
		}

		fileName = StringServices.chop(fileName, File.separator);
		int index = fileName.lastIndexOf(File.separator);

		if (index == -1) {
			return ".";
		}

		String path = fileName.substring(0, index);

		return path;

	}

	/**
	 * Die Methode <code>getExtension</code> liefert die Dateinamenerweiterung
	 * des angegebenen Pfadnamens.
	 * 
	 * Die Dateinamenerweiterung muß mit einem Punkt vom Rest getrennt sein. Der
	 * Punkt wird nicht mit zurückgeliefert.
	 */
	public static String getExtension(String filename) {

		if (filename == null) {
			throw new IllegalArgumentException("Parameter filename is null!");
		}

		// strip any directory information
		filename = getFileName(filename);

		int index = filename.lastIndexOf(".");
		if (index == -1) {
			return "";
		}

		filename = filename.substring(index + 1);
		return filename;
	}

	public static String getFileBase(File file) {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		return getFileBase(file.getPath());
	}

	/**
	 * Die Methode <code>getFileBase</code> liefert den Pfadnamen ohne
	 * Dateinamenerweiterung.
	 * 
	 * Als Dateinamenerweiterung wird der letzte Teil eines Pfadnamens
	 * angesehen, der mit einem Punkt vom Rest abgetrennt ist.
	 * 
	 * Falls der Pfad Verzeichnisse enthält werden diese nicht entfernt. Um den
	 * letzten Teil der Pfadangabe zu erhalten kann die Methode
	 * <code>getFileName</code> verwendet werden.
	 * 
	 * <div title="example">
	 * <p>
	 * Der Pfad <code>/tmp/file.txt</code> wird zu <code>/tmp/file</code>.
	 * </div>
	 * 
	 */
	public static String getFileBase(String fileName) {

		if (fileName == null) {
			throw new IllegalArgumentException("Parameter fileName is null!");
		}

		int index = fileName.lastIndexOf(".");

		if (index == -1) {
			return fileName;
		}

		fileName = fileName.substring(0, index);

		return fileName;
	}

	/**
	 * Die Methode <code>getFileName</code> liefert den letzten Teil einer
	 * Pfadangabe, üblicherweise einen Dateinamen.
	 */
	public static String getFileName(String path) {

		if (path == null) {
			throw new IllegalArgumentException("Parameter path is null!");
		}

		path = normalizePath(path);
		path = StringServices.chop(path, "/");

		File file = new File(path);
		return file.getName();
	}

	public static String getMimeType(String fileName) {

		if (fileName == null) {
			throw new IllegalArgumentException(
					"The argument fileName must not be null!");
		}

		String type = null;

		String[] cmd = new String[5];

		cmd[0] = "file";
		cmd[1] = "-b";
		cmd[2] = "-p";
		cmd[3] = "-i";
		cmd[4] = fileName;

		ExecResult result = ExecServices.exec(cmd);
		type = result.getOutput();

		type = StringServices.trim(type, Sfv.LSEP);
		type = StringServices.trim(type, ' ');

		if (result.failed() == false && type != null && type.length() > 0) {

			logger.debug("(file -b -pi -i " + fileName + ") -e type=" + type);
			return type;
		}

		String name = FileServices.getFileName(fileName.toLowerCase());
		name = StringServices.cutSuffix(name, ",v");

		FileNameMap fnm = URLConnection.getFileNameMap();
		type = fnm.getContentTypeFor(name);

		if (type != null && type.trim().length() > 0) {
			logger.debug("(FileNameMap " + name + ") type=" + type);
			return type;
		}

		String ext = FileServices.getExtension(name);
		MimeType mimeType = MimeType.findByExtension(ext);

		if (mimeType != null) {
			logger.debug("(MimeType " + name + ") type="
					+ mimeType.getMimeType());
			return mimeType.getMimeType();
		}

		File file = new File(fileName);
		boolean nullByte = false;

		if (file.exists() && file.canRead()) {

			FileReader reader = null;

			try {

				reader = new FileReader(file);
				int c = reader.read();

				while (c != -1) {

					c = reader.read();

					if (c == 0) {
						nullByte = true;
						break;
					}
				}

			} catch (Throwable oops) {

				logger.fatal("FileServices.isBinary", oops);

			} finally {

				try {

					if (reader != null) {
						reader.close();
					}

				} catch (IOException oops) {

					logger.fatal("FileServices.isBinary reader.close() ", oops);
				}
			}
		}

		if (nullByte == true) {

			logger.debug("(No MimeType Found, Null Byte) " + name
					+ ") type=application/octet-stream");
			return "application/octet-stream";

		} else {

			logger.debug("(No MimeType Found " + name + ") type=text/plain");
			return "text/plain";
		}
	}

	public static String getPathHead(String path, int length) {

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path must not be null!");
		}

		if (length < 0) {
			throw new IllegalArgumentException(
					"The argument length must not be < 0!");
		}

		path = normalizePath(path);

		String subPath = path;

		int index = path.indexOf("/");
		int counter = 0;

		while (index > 0 && counter < length) {

			index = path.indexOf("/", index + 1);
			counter++;
		}

		if (index > 0) {
			subPath = path.substring(0, index);
		}

		return subPath;
	}

	public static String getPathTail(String path, int length) {

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path must not be null!");
		}

		if (length < 0) {
			throw new IllegalArgumentException(
					"The argument length must not be < 0!");
		}

		path = StringServices.replace(path, FSEP, "/");

		String subPath = path;

		int index = path.lastIndexOf("/");
		int counter = 1;

		while (index > 0 && counter < length) {

			index = path.lastIndexOf("/", index - 1);
			counter++;
		}

		if (index > 0) {
			subPath = path.substring(index + 1);
		}

		return subPath;
	}

	/**
	 * Die Methode <code>getPathToken</code> liefert einen bestimmenten Teil
	 * eines Pfades.
	 * 
	 * Der Pfad wird von <b>hinten</b> nach vorne beginnend mit der Ziffer 1
	 * durchnummeriert.
	 * 
	 * @param path
	 *            <code>String</code>
	 * @param index
	 *            <code>int</code>
	 * @return <code>String</code>
	 */
	public static String getPathToken(String path, int index) {

		if (index == 0)
			return "";

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path must not be null!");
		}

		if (index < 0) {
			throw new IllegalArgumentException(
					"The argument index must not be < 0!");
		}

		String buffer = normalizePath(path);
		buffer = StringServices.chop(buffer, "/");

		logger.debug("buffer = " + buffer);

		int indexStart = buffer.length();
		int indexEnd = indexStart;

		for (int i = 0; i < index; i++) {

			indexEnd = indexStart;
			indexStart = buffer.lastIndexOf('/', indexStart - 1);

			logger.debug("start = " + indexStart + ", end = " + indexEnd);

			if (indexStart == -1) {
				return null;
			}
		}

		String token = buffer.substring(indexStart + 1, indexEnd);
		logger.debug("path token [" + index + "] = '" + token + "'.");

		return token;
	}

	public static int getPathTokenCount(String path) {

		logger.debug("#1 path=" + path);

		if (path == null || path.trim().length() == 0) {
			return 0;
		}

		path = normalizePath(path);
		path = StringServices.trim(path, '/');

		logger.debug("#2 path=" + path);

		int index = path.indexOf("/");
		logger.debug("index=" + index);

		int counter = 1;

		while (index > 0) {

			index = path.indexOf("/", index + 1);
			logger.debug("index=" + index);
			counter++;
		}

		logger.debug("counter=" + counter);
		return counter;
	}

	public static String getValueOfKey(File file, String key)
			throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		if (key == null) {
			throw new IllegalArgumentException(
					"The argument key must not be null!");
		}

		key = key.trim();

		if (key.endsWith("=") == false) {
			key += "=";
		}

		BufferedReader reader = null;

		try {

			reader = new BufferedReader(new FileReader(file));

			String line;

			while ((line = reader.readLine()) != null) {

				if (line.startsWith(key) == true) {

					if (line.length() <= key.length()) {
						return "";
					} else {
						return line.substring(key.length() + 1);
					}
				}
			}

			return "";

		} finally {

			try {

				if (reader != null) {
					reader.close();
				}

			} catch (IOException ioe) {

				ioe.printStackTrace();
			}
		}
	}

	public static boolean hasSubdirectory(File dir, String name) {

		if (dir == null) {
			return false;
		}

		if (name == null || name.length() == 0) {
			return false;
		}

		String[] listing = dir.list();

		if (listing == null) {
			return false;
		}

		for (int i = 0; i < listing.length; i++) {

			if (name.equals(listing[i])) {
				return true;
			}
		}

		return false;
	}

	public static String info(File file) throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		StringBuffer buffer = new StringBuffer();

		buffer.append("path=" + file.getCanonicalPath());

		if (file.exists() == false) {

			buffer.append(",exists=false");
			return buffer.toString();
		}

		buffer.append(",size=" + file.length());

		return buffer.toString();
	}

	/**
	 * Liefert <code>true</code>, falls die Zeichenkette einen absoluten Pfad
	 * repräsentiert.
	 */
	public static boolean isAbsolutePath(String path) {

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path must not be null!");
		}

		String buffer = new String(path);

		buffer = StringServices.replace(buffer, File.separator, "/");
		buffer = buffer.toLowerCase();

		if (buffer.startsWith("/")) {
			return true;
		}

		if (JvmServices.isWindows()) {

			if (PATTERN_ABSOLUTE_WINDOWS_PATH.matcher(buffer).matches()) {
				return true;
			}
		}

		return false;
	}

	public static boolean isBinary(File file) {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		return isBinary(file.getAbsolutePath());
	}

	public static boolean isBinary(String fileName) {

		if (fileName == null) {
			throw new IllegalArgumentException(
					"The argument fileName must not be null!");
		}

		String mimeType = getMimeType(fileName);

		logger.debug("mimeType=" + mimeType);

		if (mimeType.startsWith("image/")) {
			return true;
		}

		if (mimeType.startsWith("video/")) {
			return true;
		}

		if (mimeType.startsWith("audio/")) {
			return true;
		}

		if (mimeType.equals("application/octet-stream")
				|| mimeType.equals("application/pdf")
				|| mimeType.equals("application/postscript")
				|| mimeType.equals("application/x-cpio")
				|| mimeType.equals("application/x-dvi")
				|| mimeType.equals("application/x-gtar")
				|| mimeType.equals("application/x-tar")
				|| mimeType.equals("application/x-ustar")
				|| mimeType.equals("application/x-troff-msvideo")
				|| mimeType.equals("application/zip")) {
			return true;
		}

		File file = new File(fileName);
		boolean nullByte = false;

		if (file.exists() && file.canRead()) {

			FileReader reader = null;

			try {

				reader = new FileReader(file);
				int c = reader.read();

				while (c != -1) {

					c = reader.read();

					if (c == 0) {
						nullByte = true;
						break;
					}
				}

			} catch (Throwable oops) {

				logger.fatal("FileServices.isBinary", oops);

			} finally {

				try {

					if (reader != null) {
						reader.close();
					}

				} catch (IOException oops) {

					logger.fatal("FileServices.isBinary reader.close() ", oops);
				}
			}
		}

		if (nullByte == true) {
			return true;
		}

		return false;
	}

	public static boolean isEmptyDirectory(File file) {

		if (file.isDirectory() == false) {
			return false;
		}

		File[] list = file.listFiles();

		if (list.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Die Methode <code>move</code> verschiebt eine Datei in ein anderes
	 * Verzeichnis.
	 */
	public static void move(String src, String destdir) throws IOException {

		if (src == null) {
			throw new IllegalArgumentException(
					"The argument src must not be null!");
		}

		if (destdir == null) {
			throw new IllegalArgumentException(
					"The argument destdir must not be null!");
		}

		File srcFile = new File(src);
		File destFile = new File(destdir + File.separator + srcFile.getName());

		if (srcFile.equals(destFile)) {
			return;
		}

		boolean rc = srcFile.renameTo(destFile);

		if (rc == false) {
			throw new RenameFileException(srcFile, destFile);
		}
	}

	/**
	 * Die Methode <code>newer</code> vergleicht ob die 1. Datei neuer als die
	 * 2, Datei ist. Falls die 2. Datei nicht existiert wird true zurückgegeben.
	 */
	public static boolean newer(File f1, File f2) {

		if (f1 == null || f1.exists() == false) {
			return false;
		}

		if (f2 == null || f2.exists() == false) {
			return true;
		}

		if (f1.lastModified() >= f2.lastModified()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Die Methode <code>newer</code> vergleicht 2 Dateien oder Verzeichnisse
	 * bezüglich des Datums ihrer letzten Änderung.
	 * 
	 * Requires: Beide Dateien/Verzeichnisse müssen existieren.
	 */
	public static boolean newer(String f1, String f2) {

		return newer(new File(f1), new File(f2));
	}

	/**
	 * Die Methode <code>normalizePath</code> konvertiert den angegegebenen Pfad
	 * in ein Standardformat.
	 * 
	 * <ol>
	 * <li>Weißraum am Anfang oder Ende der Zeichenkette wird entfernt.</li>
	 * <li>Alle betriebssystemspezifischen Trennzeichen werden in einen
	 * Schrägstrich umgewandelt.</li>
	 * <li>Falls der Pfad einen absoluten Pfad repäsentiert, werden redundante
	 * Angaben bereinigt, z.B. <code>/C:/temp</code> wird zu
	 * <code>C:/temp</code>.</li>
	 * <ol>
	 * 
	 * @param path
	 *            <code>String</code>
	 * @return <code>String</code>
	 */
	public static String normalizePath(String path) {

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path must not be null!");
		}

		path = new String(path);
		File file = new File(path);

		path = path.trim();
		path = StringServices.replace(path, File.separator, "/");

		while (isAbsolutePath(path) && path.length() > 1) {

			String buffer = path.substring(1);

			if (isAbsolutePath(buffer)) {

				path = buffer;

			} else {

				break;
			}
		}

		if (isAbsolutePath(path)) {
			path = StringServices.lowerFirstLetter(path);
		}

		if (file.exists() && file.isDirectory() && path.endsWith("/") == false) {
			path += "/";
		}

		return path;
	}

	/**
	 * Die Methode <code>pkgToPath</code> wandelt den Namen eines Java Packages
	 * in einen Verzeichnispfad um.
	 */
	public static String pkgToPath(String pkg) {

		if (pkg == null) {
			throw new IllegalArgumentException(
					"The argument pkg must not be null!");
		}

		String path = StringServices.replace(pkg, ".", File.separator);
		return path;
	}

	public static String readLine(File file, int lineIndex) throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		if (lineIndex < 0) {
			throw new IllegalArgumentException(
					"The argument lineIndex must not be < 0!");
		}

		int count = 0;

		if (file != null && file.exists()) {

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();

			while (line != null) {

				if (count == lineIndex) {
					reader.close();
					return line;
				}

				line = reader.readLine();
				count++;
			}

			reader.close();
		}

		return null;
	}

	public static Object[] readToArray(File file) throws IOException {

		return readToArray(file, "UTF-8");
	}

	public static Object[] readToArray(File file, String encoding)
			throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		if (encoding == null) {
			throw new IllegalArgumentException(
					"The argument encoding must not be null!");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), encoding));

		Object[] array = null;

		try {

			ArrayList<String> list = new ArrayList<String>();

			String line = reader.readLine();

			while (line != null) {

				list.add(line);
				line = reader.readLine();
			}

			int index = 0;
			array = new Object[list.size()];

			Iterator<String> iterator = list.iterator();

			while (iterator.hasNext()) {

				line = iterator.next();
				array[index++] = line;
			}

		} finally {
			reader.close();
		}

		return array;
	}

	public static byte[] readToByteArray(File file) throws IOException {

		if (file == null) {
			return null;
		}

		if (file.exists() == false) {
			return null;
		}

		FileInputStream fis = new FileInputStream(file);

		int off = 0;
		long fileLength = file.length();

		if (fileLength > Integer.MAX_VALUE) {

			logger.error("The file is too big (>" + Integer.MAX_VALUE + ")!");
			fis.close();
			return null;
		}

		int length = (int) fileLength;
		int size = length;

		byte[] buffer = new byte[length];
		int count;

		try {

			while ((count = fis.read(buffer, off, size)) != -1) {

				off += count;

				if ((length - off) < size) {

					size = length - off;

					if (size < 1) {
						break;
					}
				}

				if (off < 0) {
					logger.error("Argument off must not be negative: off="
							+ off + ", count=" + count + ", len=" + size
							+ ", b.length=" + length);
				}

				if (size < 0) {
					logger.error("Argument len must not be negative: off="
							+ off + ", count=" + count + ", len=" + size
							+ ", b.length=" + length);
				}

				if (size > (length - off)) {
					logger.error("Argument len must not be negative: off="
							+ off + ", count=" + count + ", len=" + size
							+ ", b.length=" + length);
				}

			}

		} finally {
			fis.close();
		}

		return buffer;
	}

	public static String readToString(File file) throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		return readToString(file, "UTF-8");
	}

	public static String readToString(File file, String encoding)
			throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		FileInputStream fis = new FileInputStream(file);
		String buffer = readToString(fis, encoding);
		fis.close();

		return buffer;
	}

	public static String readToString(InputStream is) throws IOException {

		return readToString(is, "UTF-8");
	}

	public static String readToString(InputStream is, String encoding)
			throws IOException {

		if (is == null) {
			throw new IllegalArgumentException(
					"The argument is must not be null!");
		}

		if (encoding == null) {
			throw new IllegalArgumentException(
					"The argument encoding must not be null!");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				encoding));

		StringBuffer buffer = new StringBuffer();
		char[] data = new char[4096];
		int count;

		try {

			while ((count = reader.read(data)) > 0) {
				buffer.append(data, 0, count);
			}

		} finally {
			reader.close();
		}

		return buffer.toString();
	}

	public static String readToString(String fileName) throws IOException {

		if (fileName == null) {
			throw new IllegalArgumentException(
					"The argument fileName must not be null!");
		}

		return readToString(new File(fileName), "UTF-8");
	}

	public static String readToString(String fileName, String encoding)
			throws IOException {

		if (fileName == null) {
			throw new IllegalArgumentException(
					"The argument fileName must not be null!");
		}

		return readToString(new File(fileName), encoding);
	}

	/**
	 * Die Methode <code>relativePath</code> ermittelt den relativen Pfad um vom
	 * Verzeichnis <code>start</code> zum Verzeichnis <code>dest</code> zu
	 * gelangen. Falls kein relativer Pfad ermittelt werden kann wird der
	 * absolute Pfad von <code>dest</code> zurückgeliefert.
	 * 
	 * Werden relative Pfade übergeben, so werden diese mit Hilfe der Methode
	 * {@link File#getCanonicalFile()} in absolute PFade umgewandelt. Diese
	 * Methode ermittelt Pfad anscheinend anhand des Rückgabewertes von
	 * <code>System.getProperty("user.dir")</code>. Der Wert ist also nicht
	 * vohersagbar und ist abhängig vom aktuellen Arbeitsverzeichnis.
	 * 
	 * @throws IOException
	 */
	public static String relativePath(File start, File dest) throws IOException {

		if (start == null) {
			throw new IllegalArgumentException(
					"The argument start must not be null!");
		}

		if (dest == null) {
			throw new IllegalArgumentException(
					"The argument dest must not be null!");
		}

		File from = new File(start.getCanonicalPath());
		File to = new File(dest.getCanonicalPath());

		logger.debug("Berechne relativen Pfad von " + from + " zu " + to
				+ ".\n");

		if (from.equals(to)) {
			return ".";
		}

		File file = to;

		String downPath = "";
		String upPath = "";
		String relativePath;
		String path1;
		String path2;
		String str;

		File fromParent = from;

		if (fromParent.isFile() == true) {
			fromParent = fromParent.getParentFile();
		}

		while (fromParent != null) {

			path1 = fromParent.getCanonicalPath() + File.separator;
			path2 = to.getCanonicalPath();

			logger.debug("path1 = " + path1);
			logger.debug("path2 = " + path2);

			if (path2.startsWith(path1)) {

				str = StringServices.cutPrefix(path2, path1);

				if (downPath.length() == 0) {
					downPath = "." + File.separator;
				}

				str = appendPath(downPath, str);
				relativePath = StringServices.chop(str, File.separator);

				return relativePath;
			}

			downPath += ".." + File.separator;

			if (file != null) {
				upPath = file.getName() + File.separator + upPath;
			}

			fromParent = fromParent.getParentFile();

			if (fromParent.getParent() == null) {
				break;
			}

			if (file != null) {
				file = file.getParentFile();
			}
		}

		return dest.getCanonicalPath();
	}

	public static String removeParentPath(File dir, File parentDir)
			throws IOException {

		if (dir == null) {
			throw new IllegalArgumentException(
					"The argument dir must not be null!");
		}

		if (parentDir == null) {
			throw new IllegalArgumentException(
					"The argument parentDir must not be null!");
		}

		String parentPath = parentDir.getCanonicalPath();

		if (parentPath.endsWith(Sfv.FSEP) == false) {
			parentPath += Sfv.FSEP;
		}

		return removeParentPath(dir.getCanonicalPath(), parentPath);
	}

	public static String removeParentPath(String path, String parentPath) {

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path must not be null!");
		}

		if (parentPath == null) {
			throw new IllegalArgumentException(
					"The argument parentPath must not be null!");
		}

		path = normalizePath(path);
		parentPath = normalizePath(parentPath);

		logger.debug("path=" + path);
		logger.debug("parentPath=" + parentPath);

		path = StringServices.cutPrefix(path, parentPath);

		return path;
	}

	public static void rename(File srcFile, File destFile) throws IOException {

		if (srcFile == null) {
			throw new IllegalArgumentException(
					"The argument srcFile must not be null!");
		}

		if (destFile == null) {
			throw new IllegalArgumentException(
					"The argument destFile must not be null!");
		}

		if (srcFile.equals(destFile)) {
			logger.warn("Source file " + srcFile
					+ " is identical to destination file.");
			return;
		}

		boolean rc = srcFile.renameTo(destFile);

		if (rc == true) {
			return;
		}

		if (srcFile.isDirectory() == true) {

			copyDir(srcFile, destFile);
			delete(srcFile);
			return;
		}

		throw new RenameFileException(srcFile, destFile);
	}

	/**
	 * Die Methode <code>replaceExtension</code> ersetzt die
	 * Dateinamenserweiterung.
	 */
	public static String replaceExtension(File file, String extension) {

		return replaceExtension(file.getPath(), extension);
	}

	/**
	 * Die Methode <code>replaceExtension</code> ersetzt die
	 * Dateinamenserweiterung.
	 */
	public static String replaceExtension(String fileName, String extension) {

		if (fileName == null) {
			throw new IllegalArgumentException(
					"The argument fileName must not be null!");
		}

		if (extension == null) {
			throw new IllegalArgumentException(
					"The argument extension must not be null!");
		}

		String base = getFileBase(fileName);

		return base + extension;
	}

	public static boolean setReadOnly(File file) {

		if (file == null || file.exists() == false) {
			return false;
		}

		String[] cmd = null;
		ExecResult result = null;

		try {

			if (JvmServices.isUnix()) {

				if (file.isDirectory()) {

					cmd = new String[7];
					cmd[0] = "find";
					cmd[1] = file.getCanonicalPath();
					cmd[2] = "-exec";
					cmd[3] = "chmod";
					cmd[4] = "a-w";
					cmd[5] = "{}";
					cmd[6] = ";";

				} else {

					cmd = new String[3];
					cmd[0] = "chmod";
					cmd[1] = "a-w";
					cmd[2] = file.getCanonicalPath();
				}
			}

			if (JvmServices.isWindows()) {

				cmd = new String[4];
				cmd[0] = "attrib";
				cmd[1] = "+r";
				cmd[2] = "+h";
				cmd[3] = file.getCanonicalPath();

				if (file.isDirectory()) {

					result = ExecServices.exec(cmd);

					if (result.failed() == true) {
						IOException oops = new IOException(result.toString());
						throw oops;
					}

					cmd = new String[6];
					cmd[0] = "attrib";
					cmd[1] = "+r";
					cmd[2] = "+h";
					cmd[3] = file.getCanonicalPath() + "\\*.*";
					cmd[4] = "/S";
					cmd[5] = "/D";
				}
			}

			if (cmd == null) {
				logger.error("Operating System '"
						+ JvmServices.getOperatingSystem()
						+ "' is not supported!");
				return false;
			}

			logger.debug("Executing command '"
					+ StringServices.arrayToString(cmd) + "'...");
			result = ExecServices.exec(cmd);

			if (result.failed() == true) {
				logger.error(result.toString());
				return false;
			}

		} catch (IOException oops) {

			logger.fatal(
					"setReadOnly failed! (" + StringServices.arrayToString(cmd)
							+ ")", oops);
			return false;
		}

		return true;
	}

	public static boolean setReadOnly(String path) {

		if (path == null) {
			return false;
		}

		return setReadOnly(new File(path));
	}

	/**
	 * Die Methode <code>setWritable</code> setzt die Berechtigung Datei oder
	 * das Verzeichnis auf schreibbar. Falls eine Verzeichnis angegeben wurde,
	 * werden auch alle darin enthaltenen Dateien und Unterverzeichnisse
	 * bearbeitet.
	 * 
	 * <code>setWritable</code> verwendet Kommandos des Betriebssystems um die
	 * Berechtigungen zu ändern. Unter Windows wird das Kommando
	 * <code>attrib</code> aufgerufen und zusätzlich das Attribut
	 * <code>hidden</code> zurückgesetzt.
	 * 
	 * @param file
	 *            <code>File</code>
	 */
	public static boolean setWritable(File file) {

		if (file == null || file.exists() == false) {
			return false;
		}

		String[] cmd = null;
		ExecResult result = null;

		try {

			if (JvmServices.isUnix()) {

				if (file.isDirectory()) {

					cmd = new String[7];
					cmd[0] = "find";
					cmd[1] = file.getCanonicalPath();
					cmd[2] = "-exec";
					cmd[3] = "chmod";
					cmd[4] = "a+w";
					cmd[5] = "{}";
					cmd[6] = ";";

				} else {

					cmd = new String[3];
					cmd[0] = "chmod";
					cmd[1] = "a+w";
					cmd[2] = file.getCanonicalPath();
				}
			}

			if (JvmServices.isWindows()) {

				cmd = new String[4];
				cmd[0] = "attrib";
				cmd[1] = "-r";
				cmd[2] = "-h";
				cmd[3] = file.getCanonicalPath();

				if (file.isDirectory()) {

					result = ExecServices.exec(cmd);

					if (result.failed() == true) {
						IOException oops = new IOException(result.toString());
						throw oops;
					}

					cmd = new String[6];
					cmd[0] = "attrib";
					cmd[1] = "-r";
					cmd[2] = "-h";
					cmd[3] = file.getCanonicalPath() + "\\*.*";
					cmd[4] = "/S";
					cmd[5] = "/D";
				}
			}

			if (cmd == null) {

				logger.error("Operating System '"
						+ JvmServices.getOperatingSystem()
						+ "' is not supported!");
				return false;
			}

			logger.debug("Executing command '"
					+ StringServices.arrayToString(cmd) + "'...");
			result = ExecServices.exec(cmd);

			if (result.failed() == true) {
				logger.error(result.toString());
				return false;
			}

		} catch (IOException oops) {

			logger.fatal(
					"setWritable failed! (" + StringServices.arrayToString(cmd)
							+ ")", oops);
			return false;
		}

		return true;
	}

	public static boolean setWritable(File file, String caller) {

		logger.debug("Caller of setWritable is '" + caller + "'.");
		return setWritable(file);
	}

	public static boolean setWritable(String path) {

		if (path == null) {
			return false;
		}

		return setWritable(new File(path));
	}

	public static boolean setWritable(String path, String caller) {

		logger.debug("Caller of setWritable is '" + caller + "'.");
		return setWritable(path);
	}

	public static void sort(File file) throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		Object[] array = readToArray(file);
		Arrays.sort(array);
		writeFromArray(file, array);
	}

	public static void touch(File file) throws IOException {

		writeFromString(file, String.valueOf(System.currentTimeMillis()));
	}

	public static void touch(String fileName) throws IOException {

		if (fileName == null) {
			throw new IllegalArgumentException(
					"The argument fileName must not be null!");
		}

		touch(new File(fileName));
	}

	public static String toWindowsPath(String path) {

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path must not be null!");
		}

		String winPath = path;

		Pattern winDrivePattern = Pattern.compile("^/[a-cA-Z]:/");
		Matcher matcher = winDrivePattern.matcher(winPath);

		if (matcher.lookingAt()) {
			winPath = winPath.substring(1);
		}

		winPath = winPath.trim();
		winPath = StringServices.replace(winPath, "/", "\\");

		return winPath;
	}

	/**
	 * This method corrects the path regarding the used operating system.
	 * Windows "\" and Linux "/"
	 * 
	 * @param in
	 *            the string representing the path
	 * @return out the corrected string representing the path
	 */
	public static String correctPathSeperator(String in) {
		String os = System.getProperty("os.name");

		in = FileServices.normalizePath(in);
		if (os.toLowerCase().contains("windows")) {
			in = toWindowsPath(in);
			return in;
		}

		return in;

	}

	public static void traverse(File dir, FileVisitor visitor) {

		if (dir == null || dir.exists() == false || visitor == null) {
			return;
		}

		for (File file : dir.listFiles()) {
			visitor.visit(file);
		}

		File[] subdirs = dir.listFiles(new DirectoryFilter());

		if (subdirs != null) {
			for (File subdir : subdirs) {
				traverse(subdir, visitor);
			}
		}
	}

	public static void write(File file, InputStream instr) throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		if (instr == null) {
			throw new IllegalArgumentException(
					"The argument instr must not be null!");
		}

		File parent = file.getParentFile();

		if (parent != null) {
			createPath(parent);
		}

		FileOutputStream out = new FileOutputStream(file);

		int n = 0;
		byte[] buffer = new byte[4096];
		while ((n = instr.read(buffer, 0, 4096)) != -1) {
			out.write(buffer, 0, n);
		}

		out.flush();
		out.close();
	}

	public static void writeFromArray(File file, Object[] array)
			throws IOException {

		writeFromArray(file, array, "UTF-8");
	}

	public static void writeFromArray(File file, Object[] array, String encoding)
			throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		if (array == null) {
			throw new IllegalArgumentException(
					"The argument array must not be null!");
		}

		if (encoding == null) {
			throw new IllegalArgumentException(
					"The argument encoding must not be null!");
		}

		PrintWriter writer = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(file), encoding));

		try {

			for (int i = 0; i < array.length; i++) {
				writer.println(array[i]);
			}

		} finally {

			writer.close();
		}
	}

	public static void writeFromByteArray(File file, byte[] buffer)
			throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		if (buffer == null) {
			throw new IllegalArgumentException(
					"The argument buffer must not be null!");
		}

		FileOutputStream out = new FileOutputStream(file);
		out.write(buffer);
		out.close();
	}

	public static void writeFromStream(String fileName, InputStream instr)
			throws IOException {

		if (fileName == null) {
			throw new IllegalArgumentException(
					"The argument fileName must not be null!");
		}

		if (instr == null) {
			throw new IllegalArgumentException(
					"The argument instr must not be null!");
		}

		FileOutputStream out = new FileOutputStream(fileName);

		int n = 0;
		byte[] buffer = new byte[4096];
		while ((n = instr.read(buffer, 0, 4096)) != -1) {
			out.write(buffer, 0, n);
		}

		out.close();
	}

	public static void writeFromString(File file, String str)
			throws IOException {

		writeFromString(file, str, "UTF-8");
	}

	/*
	 * Die Methode schreibt einen String in eine Datei
	 * 
	 * @param File die Datei in die geschrieben wird
	 * 
	 * @param String der zu schreibende String
	 * 
	 * @param String die zu benutzende Zeichenkodierung (Standard UTF-8)
	 */
	public static void writeFromString(File file, String str, String encoding)
			throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		File parent = file.getParentFile();

		if (parent != null && parent.exists() == false) {
			createPath(parent);
		}

		FileOutputStream fos = new FileOutputStream(file);
		writeFromString(fos, str, encoding);
		fos.close();
	}

	public static void writeFromString(OutputStream os, String str)
			throws IOException {

		writeFromString(os, str, "UTF-8");
	}

	public static void writeFromString(OutputStream os, String str,
			String encoding) throws IOException {

		if (os == null) {
			throw new IllegalArgumentException(
					"The argument os must not be null!");
		}

		if (str == null) {
			throw new IllegalArgumentException(
					"The argument str must not be null!");
		}

		if (encoding == null) {
			throw new IllegalArgumentException(
					"The argument encoding must not be null!");
		}

		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os,
				encoding));

		try {

			writer.print(str);

		} finally {

			writer.close();
		}
	}

	public static void writeFromString(String fileName, String str)
			throws IOException {

		if (fileName == null) {
			throw new IllegalArgumentException(
					"The argument fileName must not be null!");
		}

		writeFromString(new File(fileName), str);
	}

	public static void writeFromString(String fileName, String str,
			String encoding) throws IOException {

		if (fileName == null) {
			throw new IllegalArgumentException(
					"The argument fileName must not be null!");
		}

		writeFromString(new File(fileName), str, encoding);
	}
}
