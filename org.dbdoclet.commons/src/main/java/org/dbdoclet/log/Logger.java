/* 
 * ### Copyright (C) 2006-2010 Michael Fuchs ###
 * ### All Rights Reserved.                  ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.dbdoclet.io.Screen;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;

/**
 * Die Klasse <code>Logger</code> dient der Protokollierung von Klassen und
 * Methoden. Wird im Classpath eine Datei namens Logger.properties gefunden,
 * wird diese ausgewertet. Ein typischer Eintrag lautet:
 * 
 * <pre>
 * org.dbdoclet.cocs.cob.CobContext.level=DEBUG
 * org.dbdoclet.cocs.cob.CobContext.level=DEBUG(enablePlugins)
 * </pre>
 * 
 * <dl>
 * <dt>visibility.method</dt>
 * <dd>(true|false) Bestimmt ob der Name der aufrufenden Methode angezeigt wird
 * oder nicht.</dd>
 * </dl>
 * 
 * @author <a href="mailto:michael.fuchs@unico-group.com">Michael Fuchs</a>
 * @version 1.0
 */
@Deprecated
public class Logger {

	private static final String LSEP = System.getProperty("line.separator");

	private static Map<String, Logger> loggerMap = Collections
			.synchronizedMap(new TreeMap<String, Logger>());
	private static Properties config = new Properties();

	public static final int FATAL = 1;
	public static final int ERROR = 2;
	public static final int WARN = 3;
	public static final int INFO = 4;
	public static final int DEBUG = 5;
	public static final int DEBUG2 = 6;
	public static final int DEBUG3 = 7;
	public static final int DEBUG4 = 8;
	public static final int DEBUG5 = 9;

	public static final int SHORT = 1;
	public static final int MEDIUM = 2;
	public static final int LONG = 3;

	private static boolean trace = false;
	private static PrintWriter rootWriter = null;
	private static Screen rootScreen;
	private static SimpleDateFormat datefmt = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss SSS");
	private static boolean isMethodNameVisible = false;
	private static int rootLogLevel = ERROR;
	private static int rootOutputFormat = MEDIUM;
	private static boolean outputEnabled = true;
	private static boolean lineWrapEnabled = true;

	private final String name;
	private HashMap<String, String> methodMap;
	private int outputFormat = MEDIUM;
	private final PrintWriter writer;
	private int logLevel = ERROR;

	static {

		try {

			String value = System.getProperty("logger.debug");

			if (value != null && value.equalsIgnoreCase("true")) {
				trace = true;
				ptrace("Debugging Logger enabled.");
			}

			InputStream instr = ResourceServices
					.getResourceAsStream("Logger.properties");

			if (instr != null) {

				ptrace(String.format("Loading configuration %s...",
						ResourceServices.getResourceAsUrl("Logger.properties")));
				readConfiguration(instr);

			} else {

				ptrace("File Logger.properties not found.");
			}

		} catch (Throwable oops) {
			// oops.printStackTrace();
		}
	}

	private Logger(Class<?> clazz) {
		this(clazz.getName(), clazz, null);
	}

	private Logger(String name) {
		this(name, null, null);
	}

	private Logger(String name, Class<?> clazz, PrintWriter writer) {

		if (name == null) {
			throw new IllegalArgumentException(
					"The argument name must not be null!");
		}

		this.name = name;
		this.writer = writer;

		String level = config.getProperty(name + ".level");
		// trace("Property: " + name + ".level = " + level);

		if (level != null) {
			applyLevelInfo(this, level);
		}
	}

	private Logger(String name, PrintWriter writer) {
		this(name, null, writer);
	}

	public static String createLine(int level, int outputFormat, String name,
			HashMap<String, String> methodMap, Object msg) {

		String line;
		int index;

		if ((rootOutputFormat == SHORT || outputFormat == SHORT)
				&& level == INFO) {

			line = "";

		} else {

			line = "[" + levelToString(level) + " "
					+ datefmt.format(new Date()) + "] ";
		}

		if (isMethodNameVisible == true && level == DEBUG) {

			Throwable throwable = new Throwable();
			StringWriter buffer = new StringWriter();
			throwable.printStackTrace(new PrintWriter(buffer));

			BufferedReader reader = new BufferedReader(new StringReader(
					buffer.toString()));

			try {

				String token = reader.readLine();
				token = reader.readLine();

				while (token != null) {

					if (token.indexOf("org.dbdoclet.log.Logger") != -1) {
						token = reader.readLine();
					} else {
						break;
					}
				}

				if (token != null) {

					token = token.trim();
					token = StringServices.cutPrefix(token, "at ");
					token = StringServices.cutSuffix(token, "(Unknown Source)");

					String method = StringServices.cutPrefix(token, name);
					method = StringServices.cutPrefix(method, ".");

					index = method.indexOf('(');

					if (index > 0) {
						method = method.substring(0, method.indexOf('('));
					}

					if (methodMap != null && methodMap.get(method) == null) {
						return null;
					}

					line += " <at " + token + "> ";
				}

			} catch (IOException ignored) {
				// Ignoriert
			}

		} else {

			if ((rootOutputFormat != SHORT && outputFormat != SHORT)
					|| level == DEBUG) {
				if (name != null && name.length() > 0) {
					line += " (" + name + ") ";
				}
			}
		}

		if (lineWrapEnabled == true) {
			line += LSEP + msg;
		} else {
			line += " " + msg;
		}

		return line;
	}

	public static boolean exists(String name) {

		if (name == null || name.length() == 0) {
			return false;
		}

		Logger logger = loggerMap.get(name);

		if (logger != null) {
			return true;
		}

		return false;
	}

	public static Logger getLogger(Class<? extends Object> clazz, int level) {

		Logger logger = getLogger(clazz);
		logger.setLogLevel(level);

		return logger;
	}

	public static Logger getLogger(Class<?> clazz) {

		if (clazz == null) {
			throw new IllegalArgumentException(
					"The argument clazz may not be null!");
		}

		String name = clazz.getName();

		Logger logger;

		logger = loggerMap.get(name);

		if (logger == null) {
			logger = new Logger(clazz);
			loggerMap.put(logger.getName(), logger);
		}

		return logger;
	}

	public static Logger getLogger(String name) {

		if (name == null) {
			throw new IllegalArgumentException(
					"The argument name must not be null!");
		}

		Logger logger;

		logger = loggerMap.get(name);

		if (logger == null) {
			logger = new Logger(name);
			loggerMap.put(logger.getName(), logger);
		}

		return logger;
	}

	public static Logger getLogger(String name, int level) {

		Logger logger = getLogger(name);
		logger.setLogLevel(level);

		return logger;
	}

	public static String levelToString(int level) {

		String label = "";

		switch (level) {

		case FATAL:
			label = "FATAL ";
			break;

		case ERROR:
			label = "ERROR ";
			break;

		case WARN:
			label = "WARN  ";
			break;

		case INFO:
			label = "INFO  ";
			break;

		case DEBUG:
		case DEBUG2:
		case DEBUG3:
		case DEBUG4:
		case DEBUG5:
			label = "DEBUG ";
			break;

		default:
			label = String.valueOf(level);
		}

		return label;
	}

	@Deprecated
	public static void log(int level, int outputFormat, int logLevel, Object msg) {

		Logger logger = new Logger("");
		logger.setLogLevel(logLevel);
		logger.setOutputFormat(outputFormat);

		log(level, logger, msg);
	}

	public static void log(int level, int logLevel, Object msg, Throwable oops) {

		Logger logger = new Logger("");
		logger.setLogLevel(logLevel);

		log(level, logger, msg, oops);
	}

	public static void log(int level, Logger logger, Object msg) {

		if (msg == null) {
			throw new IllegalArgumentException(
					"The argument msg may not be null!");
		}

		int logLevel = logger.getLogLevel();
		String name = logger.getName();
		PrintWriter writer = logger.getWriter();

		// System.out.println("level..........: " + levelToString(level)
		// + LSEP + "logLevel.......: " + levelToString(logLevel)
		// + LSEP + "rootLogLevel...: " + levelToString(logLevel)
		// + LSEP + "rootWriter.....: " + rootWriter + LSEP
		// + "writer.........: " + writer + LSEP + "name...........: "
		// + name + LSEP + "msg............: " + msg);

		if (level <= rootLogLevel || level <= logLevel) {

			String line = createLine(level, logger.getOutputFormat(), name,
					logger.getMethodMap(), msg);

			if (line != null) {
				printLine(writer, level, line);
			}
		}
	}

	public static void log(int level, Logger logger, Object msg, Throwable oops) {

		if (msg == null) {
			throw new IllegalArgumentException(
					"The argument msg may not be null!");
		}

		if (oops == null) {
			throw new IllegalArgumentException(
					"The argument oops may not be null!");
		}

		HashMap<String, String> methodMap = logger.getMethodMap();
		PrintWriter writer = logger.getWriter();
		String name = logger.getName();
		int logLevel = logger.getLogLevel();

		if (level <= rootLogLevel || level <= logLevel) {

			String line = createLine(level, logger.getOutputFormat(), name,
					methodMap, msg);

			if (line != null) {

				printLine(writer, level, line);

				StringWriter buffer = new StringWriter();
				oops.printStackTrace(new PrintWriter(buffer));
				printLine(writer, level, buffer.toString());
			}
		}
	}

	public static void log(int level, Object msg) {

		Logger logger = new Logger("");
		logger.setLogLevel(rootLogLevel);

		log(level, logger, msg);
	}

	public static void log(int level, Object msg, Throwable oops) {

		Logger logger = new Logger("");
		logger.setLogLevel(rootLogLevel);

		log(level, logger, msg, oops);
	}

	public static void log(Object msg) {

		Logger logger = new Logger("");
		logger.setLogLevel(rootLogLevel);

		log(INFO, logger, msg);
	}

	public static Logger newLogger(String name, PrintWriter writer) {

		if (name == null) {
			throw new IllegalArgumentException(
					"The argument name may not be null!");
		}

		if (writer == null) {
			throw new IllegalArgumentException(
					"The argument writer may not be null!");
		}

		Logger logger = new Logger(name, writer);
		loggerMap.put(logger.getName(), logger);

		return logger;
	}

	public static void printLine(PrintWriter writer, int logLevel, String line) {

		if (writer != null) {

			if (outputEnabled == true) {
				writer.println(line);
				writer.flush();
			}

		} else if (rootWriter != null) {

			if (outputEnabled == true) {
				rootWriter.println(line);
				rootWriter.flush();
			}

		} else {

			println(line);
		}

		if (rootScreen != null) {

			if (outputEnabled == true) {

				if (logLevel == DEBUG || logLevel == INFO) {
					rootScreen.info(line);
				}

				if (logLevel == WARN) {
					rootScreen.warning(line);
				}

				if (logLevel == ERROR) {
					rootScreen.error(line);
				}

				if (logLevel == FATAL) {
					rootScreen.error(line);
				}
			}
		}
	}

	public static void println(Object msg) {

		if (msg == null) {
			throw new IllegalArgumentException(
					"The argument msg may not be null!");
		}

		if (outputEnabled == true) {
			System.out.println(msg.toString());
		}
	}

	public static void printStackTrace() {

		Throwable throwable = new Throwable();
		StringWriter buffer = new StringWriter();
		throwable.printStackTrace(new PrintWriter(buffer));

		if (rootWriter != null) {

			if (outputEnabled == true) {
				rootWriter.println(buffer);
			}

		} else {

			println(buffer);
		}

		if (rootScreen != null) {

			if (outputEnabled == true) {
				rootScreen.error(buffer.toString());
			}
		}
	}

	public static void readConfiguration(File configFile)
			throws FileNotFoundException {

		if (configFile != null && configFile.exists()) {
			readConfiguration(new FileInputStream(configFile));
		}
	}

	public static void readConfiguration(InputStream instr) {

		if (instr == null) {
			return;
		}

		try {

			if (config == null) {
				config = new Properties();
			}

			config.load(instr);

			Logger logger;
			String name;
			String property;
			String value;

			for (Enumeration<?> e = config.propertyNames(); e.hasMoreElements();) {

				property = (String) e.nextElement();

				if (property.endsWith(".level")) {

					name = StringServices.chop(property, ".level");

					value = config.getProperty(property);

					logger = loggerMap.get(name);

					if (logger != null) {
						applyLevelInfo(logger, value);
					}
				}

				if (property.equals("visibility.method") == true) {

					value = config.getProperty(property);
					isMethodNameVisible = new Boolean(value).booleanValue();
				}

				if (property.equals("output.enabled") == true) {

					value = config.getProperty(property);
					outputEnabled = new Boolean(value).booleanValue();
				}

				if (property.equals("logger.rootLogLevel") == true) {

					value = config.getProperty(property);
					setRootLogLevel(Logger.valueOf(value));
				}

				if (property.equals("logger.file") == true) {

					value = config.getProperty(property);
					value = StringServices.replace(value, "${catalina.base}",
							System.getProperty("catalina.base", "/tmp"));

					setRootWriter(new PrintWriter(new FileWriter(value)));
				}
			}

		} catch (IOException oops) {

			oops.printStackTrace();
		}
	}

	/**
	 * Bestimmt, ob ein Zeilenumbruch zwischen der Metainformation und dem
	 * Meldungstext erzeugt werden soll.
	 * 
	 * @param lineWrapEnabled
	 */
	public static void setLineWrapEnabled(boolean lineWrapEnabled) {
		Logger.lineWrapEnabled = lineWrapEnabled;
	}

	public static void setRootLogLevel(int level) {
		rootLogLevel = level;
	}

	public static void setLoggerLevel(Class<?> clazz, int level) {

		if (clazz == null) {
			return;
		}

		Logger logger = loggerMap.get(clazz.getName());

		if (logger != null) {
			logger.setLogLevel(level);
		} else {
			config.setProperty(clazz.getName() + ".level", levelAsText(level));
		}
	}

	private static String levelAsText(int level) {

		switch (level) {
		case DEBUG5:
			return "debug5";
		case DEBUG4:
			return "debug4";
		case DEBUG3:
			return "debug3";
		case DEBUG2:
			return "debug2";
		case DEBUG:
			return "debug";
		case INFO:
			return "info";
		case WARN:
			return "warn";
		case ERROR:
			return "error";
		case FATAL:
			return "fatal";
		}

		return "error";
	}

	public static void setRootOutputFormat(int rootOutputFormat) {

		if (rootOutputFormat >= SHORT && rootOutputFormat <= LONG) {
			Logger.rootOutputFormat = rootOutputFormat;
		}
	}

	public static void setRootScreen(Screen screen) {

		Logger.rootScreen = screen;
	}

	public static void setRootWriter(PrintWriter writer) {

		rootWriter = writer;
	}

	public static int valueOf(String name) {

		if (name == null) {
			throw new IllegalArgumentException(
					"The argument name must not be null!");
		}

		if (name.equalsIgnoreCase("fatal")) {
			return FATAL;
		}

		if (name.equalsIgnoreCase("error")) {
			return ERROR;
		}

		if (name.equalsIgnoreCase("warn")) {
			return WARN;
		}

		if (name.equalsIgnoreCase("info")) {
			return INFO;
		}

		if (name.equalsIgnoreCase("debug")) {
			return DEBUG;
		}

		if (name.equalsIgnoreCase("debug2")) {
			return DEBUG2;
		}

		if (name.equalsIgnoreCase("debug3")) {
			return DEBUG3;
		}

		if (name.equalsIgnoreCase("debug4")) {
			return DEBUG4;
		}

		if (name.equalsIgnoreCase("debug5")) {
			return DEBUG5;
		}

		System.err.println("[WARNING] Unknown logging level '" + name
				+ "'. Using INFO.");
		return INFO;
	}

	private static void applyLevelInfo(Logger logger, String info) {

		if (logger == null) {
			throw new IllegalArgumentException(
					"The argument logger must not be null!");
		}

		if (info == null) {
			throw new IllegalArgumentException(
					"The argument info must not be null!");
		}

		int index = info.indexOf('(');

		if (index != -1) {

			int indexParamsStart = index + 1;
			int indexParamsEnd = info.length() - 1;
			String params = "";

			if (indexParamsStart > 0 && indexParamsEnd > 0
					&& indexParamsStart < indexParamsEnd) {

				params = info.substring(indexParamsStart, indexParamsEnd);
				logger.addLogMethod(params);
			}

			info = info.substring(0, index);
		}

		logger.setLogLevel(Logger.valueOf(info));
	}

	private static void ptrace(String msg) {

		if (trace == true) {
			System.out.println(msg);
		}
	}

	public void addLogMethod(String param) {

		if (methodMap == null) {
			methodMap = new HashMap<String, String>();
		}

		methodMap.put(param, param);
	}

	public void debug5(Object msg) {
		log(DEBUG5, this, msg);
	}

	public void debug4(Object msg) {
		log(DEBUG4, this, msg);
	}

	public void debug3(Object msg) {
		log(DEBUG3, this, msg);
	}

	public void debug2(Object msg) {
		log(DEBUG2, this, msg);
	}

	public void debug(Object msg) {
		log(DEBUG, this, msg);
	}

	public void error(Object msg) {
		log(ERROR, this, msg);
	}

	public void error(Object msg, Throwable oops) {
		log(ERROR, this, msg, oops);
	}

	public void fatal(Object msg) {
		log(FATAL, this, msg);
	}

	public void fatal(Object msg, Throwable oops) {
		log(FATAL, this, msg, oops);
	}

	public int getLogLevel() {
		return logLevel;
	}

	public HashMap<String, String> getMethodMap() {
		return methodMap;
	}

	public String getName() {
		return name;
	}

	public int getOutputFormat() {
		return outputFormat;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void info(Object msg) {
		log(INFO, this, msg);
	}

	/**
	 * Die Methode <code>printList</code> erzeugt einen Protokolleintrag über
	 * den Inhalt der angegebenen Liste.
	 * 
	 * @param level
	 *            <code>int</code>
	 * @param list
	 *            <code>List</code>
	 */
	public void printList(int level, List<Object> list) {

		String msg = "";

		if (list != null) {

			Object value;
			Iterator<Object> iterator = list.iterator();

			while (iterator.hasNext()) {

				value = iterator.next();

				if (value == null) {
					msg += "null" + LSEP;
				} else {
					msg += value + " [" + value.getClass().getName() + "]"
							+ LSEP;
				}
			}
		}

		log(level, this, msg);
	}

	public void setLogLevel(int level) {
		logLevel = level;
	}

	public void setOutputFormat(int outputFormat) {

		if (outputFormat >= SHORT && outputFormat <= LONG) {
			this.outputFormat = outputFormat;
		}
	}

	public void setVar(String name, Object var) {

		if (name == null) {
			name = "???name???";
		}

		if (var == null) {
			var = "[:null:]";
		}

		debug("Der Variablen " + name + " wurde der Wert '" + var
				+ "' zugewiesen.");
	}

	public void warn(Object msg) {
		log(WARN, this, msg);
	}

	public void warn(Object msg, Throwable oops) {
		log(WARN, this, msg, oops);
	}
}
