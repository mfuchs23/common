/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.dbdoclet.Sfv;
import org.dbdoclet.progress.InfoListener;

public class ExecServices {

	public static void sleep(int secs) {

		try {
			Thread.sleep(secs * 1000);
		} catch (InterruptedException oops) {
			oops.printStackTrace();
		}
	}

	/**
	 * Ausführen eines externen Programmes im aktuellen Arbeitsverzeichnis.
	 * 
	 * @param cmd
	 * @return
	 */
	public static ExecResult exec(String cmd) {
		return exec(cmd, new File("."), false, null);
	}

	public static ExecResult exec(String cmd, InfoListener listener) {
		return exec(cmd, new File("."), false, listener);
	}

	public static ExecResult exec(String cmd, File workDir,
			InfoListener listener) {
		return exec(cmd, workDir, false, listener);
	}

	public static ExecResult exec(String cmd, boolean background) {
		return exec(cmd, new File("."), background, null);
	}

	public static ExecResult exec(String cmd, File workDir, boolean background) {

		return exec(cmd, workDir, background, null);
	}

	public static ExecResult exec(String cmd, File workDir, boolean background,
			InfoListener listener) {

		if (cmd == null) {
			throw new IllegalArgumentException(
					"The argument cmd may not be null!");
		}

		if (workDir == null) {
			workDir = new File(".");
		}

		StringTokenizer stz = new StringTokenizer(cmd);
		ArrayList<String> cmdList = new ArrayList<String>();

		String token;

		while (stz.hasMoreTokens()) {

			token = stz.nextToken();

			if (token.startsWith("-") && token.endsWith("\"")) {

				token = StringServices.cut(token, "\"");
				token = StringServices.cutSuffix(token, "\"");
			}

			if (token.startsWith("\"")) {

				while (stz.hasMoreTokens()) {

					token += " " + stz.nextToken();

					if (token.endsWith("\"")) {
						break;
					}
				}
			}

			token = StringServices.trim(token, '"');
			cmdList.add(token);
		}

		String[] cmdArray = ArrayServices.listToStringArray(cmdList);
		return exec(cmdArray, null, workDir, background, listener);
	}

	public static ExecResult exec(String[] cmd) {

		if (cmd == null) {
			throw new IllegalArgumentException(
					"The argument cmd may not be null!");
		}

		return exec(cmd, null, null, false);
	}

	public static ExecResult exec(String[] cmd, boolean background) {

		if (cmd == null) {
			throw new IllegalArgumentException(
					"The argument cmd may not be null!");
		}

		return exec(cmd, null, null, background);
	}

	public static ExecResult exec(String[] cmd, String path) {

		if (cmd == null) {
			throw new IllegalArgumentException(
					"The argument cmd may not be null!");
		}

		if (path == null) {
			throw new IllegalArgumentException(
					"The argument path may not be null!");
		}

		return exec(cmd, null, new File(path), false);
	}

	public static ExecResult exec(String[] cmd, String[] envp, File dir) {

		return exec(cmd, envp, dir, false);
	}

	public static ExecResult exec(String[] cmd, String[] envp, File dir,
			boolean background) {

		return exec(cmd, envp, dir, background, null);
	}

	public static ExecResult exec(String[] cmd, String[] envp, File dir,
			boolean background, InfoListener listener) {

		if (cmd == null) {
			throw new IllegalArgumentException(
					"The argument cmd may not be null!");
		}

		StringBuilder buffer = new StringBuilder();
		buffer.append("Executing \"");
		for (int i = 0; i < cmd.length; i++) {
			buffer.append("'");
			buffer.append(cmd[i]);
			buffer.append("' ");
		}

		buffer.append("\"");

		Process process = null;
		int exitCode = 0;

		ExecResult result = new ExecResult();
		result.setCommand(StringServices.arrayToString(cmd));

		StdInput stdout = null;
		StdInput stderr = null;

		try {

			Runtime runtime = Runtime.getRuntime();

			if (envp == null && dir == null) {
				process = runtime.exec(cmd);
			}

			if (envp == null && dir != null) {

				// envp = new String[0];
				process = runtime.exec(cmd, envp, dir);
			}

			if (envp != null && dir == null) {

				dir = new File(".");
				process = runtime.exec(cmd, envp, dir);
			}

			if (envp != null && dir != null) {

				process = runtime.exec(cmd, envp, dir);
			}

			result.setProcess(process);

			stdout = new StdInput(process.getInputStream(), listener, result);
			stderr = new StdInput(process.getErrorStream(), listener, result);

			stdout.start();
			stderr.start();

			if (background == false) {

				process.waitFor();

				stdout.join(10000);
				stderr.join(10000);
			}

		} catch (Throwable oops) {

			result.setThrowable(oops);
			result.setExitCode(-1);

		} finally {

			if (background == false) {

				try {

					if (stdout != null) {
						stdout.close();
					}

					if (stderr != null) {
						stderr.close();
					}

					if (process != null) {

						exitCode = process.exitValue();
						result.setExitCode(exitCode);

					} else {

						result.setExitCode(-1);
					}

				} catch (IllegalThreadStateException itse) {

					process.destroy();
				}
			}
		}

		return result;
	}

	public static ExecResult open(File file) throws IOException {

		if (file == null) {
			throw new IllegalArgumentException(
					"The argument file must not be null!");
		}

		String[] cmd;

		ExecResult result = null;
		String path = file.getCanonicalPath();

		if (JvmServices.isWindows()) {

			if (file.isDirectory()) {

				cmd = new String[2];
				cmd[0] = "explorer";
				cmd[1] = path;

			} else {

				cmd = new String[3];
				cmd[0] = "cmd";
				cmd[1] = "/c";
				cmd[2] = path;
			}

			result = ExecServices.exec(cmd);
		}

		if (JvmServices.isUnix()) {

			String desktopSession = System.getProperty("desktop.session");

			if (desktopSession != null
					&& desktopSession.equalsIgnoreCase("gnome")) {

				cmd = new String[2];
				cmd[0] = "gnome-open";
				cmd[1] = path;

				result = ExecServices.exec(cmd);

			} else if (desktopSession != null
					&& desktopSession.equalsIgnoreCase("kde")) {

				cmd = new String[3];
				cmd[0] = "kfmclient";
				cmd[1] = "newTab";
				cmd[2] = path;

				result = ExecServices.exec(cmd);

			} else {

				cmd = new String[2];
				cmd[0] = "gnome-open";
				cmd[1] = path;

				result = ExecServices.exec(cmd);

				if (result.failed()) {

					cmd = new String[3];
					cmd[0] = "kfmclient";
					cmd[1] = "newTab";
					cmd[2] = path;

					result = ExecServices.exec(cmd);
				}
			}
		}

		return result;
	}

	public static void invokeInfoViewer(final InfoListener viewer,
			final InputStream instr) {

		if (viewer == null) {
			throw new IllegalArgumentException(
					"The argument viewer must not be null!");
		}

		if (instr == null) {
			throw new IllegalArgumentException(
					"The argument instr must not be null!");
		}

		Thread t = new Thread() {

			@Override
			public void run() {

				BufferedReader reader = null;

				try {

					reader = new BufferedReader(new InputStreamReader(instr));

					String line = reader.readLine();

					while (line != null) {

						viewer.info(line);
						line = reader.readLine();
					}

				} catch (Exception oops) {

					oops.printStackTrace();

				} finally {

					if (reader != null) {

						try {
							reader.close();
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}
				}
			}
		};

		t.start();
	}

}

class StdInput extends Thread {

	private BufferedReader reader;
	private InfoListener listener;
	private ExecResult result;
	private boolean doClose = false;

	public StdInput(InputStream instr, InfoListener listener, ExecResult result) {

		if (instr == null) {
			throw new IllegalArgumentException(
					"The argument instr must not be null!");
		}

		if (result == null) {
			throw new IllegalArgumentException(
					"The argument result may not be null!");
		}

		this.result = result;
		this.listener = listener;

		reader = new BufferedReader(new InputStreamReader(instr));
	}

	public void close() {

		doClose = true;

		try {
			reader.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	@Override
	public void run() {

		try {

			String line = reader.readLine();

			while (line != null && doClose == false) {

				if (listener != null) {
					listener.info(line);
				}

				result.appendOutput(line + Sfv.LSEP);
				line = reader.readLine();
			}

		} catch (Exception oops) {

			oops.printStackTrace();

		} finally {

			if (reader != null) {

				try {
					reader.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
}
