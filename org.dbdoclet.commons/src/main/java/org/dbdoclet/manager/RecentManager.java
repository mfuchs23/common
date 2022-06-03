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
package org.dbdoclet.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import org.dbdoclet.service.FileServices;

public class RecentManager implements Serializable {

	private final static String home = System.getProperty("user.home");

	private static final long serialVersionUID = 3L;

	private String fileName = "recent.bin";
	private String id = ".recent";
	private int maxListSize = 10;
	private ArrayList<String> recentList = new ArrayList<String>();

	public RecentManager(String id) throws IOException {
		this(id, "recent.bin", 10);
	}

	public RecentManager(String id, String fileName) throws IOException {
		this(id, fileName, 10);
	}

	public RecentManager(String id, String fileName, int size) throws IOException {

		this.id = id;
		this.fileName = fileName;
		this.maxListSize = size;
		load(id, fileName, maxListSize);
	}

	public void add(File file) throws IOException {
		add(file.getCanonicalPath());
	}

	private void add(String name) {

		if (name == null) {
			throw new IllegalArgumentException(
					"The argument name may not be null!");
		}

		if (recentList == null) {
			throw new IllegalStateException("The field recent may not be null!");
		}

		while (recentList.size() > maxListSize) {
			recentList.remove(recentList.size() - 1);
		}

		if (recentList.contains(name)) {
			recentList.remove(name);
		}

		recentList.add(0, name);
	}

	public ArrayList<String> getList() {
		return recentList;
	}

	private File getRecentFile(String id, String fileName) throws IOException {

		if (id == null || fileName == null) {
			return null;
		}

		String fname = "";

		if ((home != null) && (home.length() > 0)) {

			fname = FileServices.appendPath(home, id);
			FileServices.createPath(fname);
			fname = FileServices.appendPath(fname, fileName);
		}

		File file = new File(fname);

		if (file.exists() && file.canRead() && file.canWrite()) {
			return file;
		}

		return null;
	}

	private void load(String id, String fileName, int size) throws IOException {

		if (id == null) {
			throw new IllegalArgumentException(
					"The argument id may not be null!");
		}

		if (fileName == null) {
			throw new IllegalArgumentException(
					"The argument fileName may not be null!");
		}

		if (size < 10 || size > 32) {
			size = 10;
		}

		recentList = new ArrayList<String>();

		File file = getRecentFile(id, fileName);
		
		if (file != null) {
			readFile(file);
        }
	}

	private void readFile(File file) throws FileNotFoundException, IOException {

		if (file.exists() && file.canRead()) {

			if (recentList == null) {
				recentList = new ArrayList<String>();
			}

			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				
				String line = reader.readLine();

				while (line != null) {

					File recentFile = new File(line);

					if (recentFile.exists() && recentFile.canRead()) {
						recentList.add(line);
					}

					line = reader.readLine();
				}
			}
		}
	}

	public boolean remove(String name) {

		if (name == null || recentList == null) {
			return false;
		}

		if (recentList.contains(name)) {
			recentList.remove(name);
			return true;
		}

		return false;
	}

	public void save() throws IOException {

		File file = getRecentFile(id, fileName);

		if (file != null) {
			writeFile(file);
		}
	}

	private void writeFile(File file) throws IOException {

		if (file == null || recentList == null) {
			return;
		}

		try(PrintWriter writer = new PrintWriter(new FileWriter(file))) {
			for (String line : recentList) {
				if (line != null) {
					writer.println(line);
				}
			}
		}
	}
}
