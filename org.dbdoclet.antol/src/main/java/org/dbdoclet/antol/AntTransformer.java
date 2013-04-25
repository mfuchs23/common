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
package org.dbdoclet.antol;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.antol.ant.Dirset;
import org.dbdoclet.antol.ant.DirsetItem;
import org.dbdoclet.antol.ant.Exclude;
import org.dbdoclet.antol.ant.Fileset;
import org.dbdoclet.antol.ant.FilesetItem;
import org.dbdoclet.antol.ant.Include;
import org.dbdoclet.antol.ant.Packageset;
import org.dbdoclet.antol.ant.PackagesetItem;
import org.dbdoclet.io.FileSet;
import org.dbdoclet.service.StringServices;

public class AntTransformer {

	private static Log logger = LogFactory.getLog(AntTransformer.class
			.getName());

	public static Collection<? extends FileSet> toFileSetList(Fileset fileset,
			File wdir) {

		if (fileset == null) {
			throw new IllegalArgumentException(
					"The argument fileset may not be null!");
		}

		if (wdir == null) {
			throw new IllegalArgumentException(
					"The argument wdir may not be null!");
		}

		logger.debug("Working directory = " + wdir.getAbsolutePath());

		ArrayList<FileSet> list = new ArrayList<FileSet>();

		FilesetItem[] items = fileset.getFilesetItem();
		FileSet fset;

		if (items.length == 0) {

			fset = new FileSet(wdir, fileset.getDir());
			list.add(fset);
			return list;
		}

		Include include;
		Exclude exclude;
		String filter;

		for (int i = 0; i < items.length; i++) {

			fset = new FileSet(wdir, fileset.getDir());

			if (fileset.hasCasesensitive()) {
				fset.setCaseSensitive(fileset.getCasesensitive());
			}

			list.add(fset);

			include = items[i].getInclude();
			if (include != null) {
				fset.setFilterType(FileSet.FILTER_INCLUDE_FILES);
				filter = include.getName();
				fset.setFilter(filter);
			}

			exclude = items[i].getExclude();
			if (exclude != null) {
				fset.setFilterType(FileSet.FILTER_EXCLUDE_FILES);
				filter = exclude.getName();
				fset.setFilter(filter);
			}
		}

		return list;
	}

	public static Collection<? extends FileSet> toFileSetList(Dirset dirset,
			File wdir) {

		if (dirset == null) {
			throw new IllegalArgumentException(
					"The argument dirset may not be null!");
		}

		if (wdir == null) {
			throw new IllegalArgumentException(
					"The argument wdir may not be null!");
		}

		logger.debug("Working directory = " + wdir.getAbsolutePath());

		ArrayList<FileSet> list = new ArrayList<FileSet>();

		DirsetItem[] items = dirset.getDirsetItem();
		FileSet fset;

		if (items.length == 0) {

			fset = new FileSet(wdir, dirset.getDir());
			list.add(fset);
			return list;
		}

		Include include;
		Exclude exclude;
		String filter;

		for (int i = 0; i < items.length; i++) {

			fset = new FileSet(wdir, dirset.getDir());

			if (dirset.hasCasesensitive()) {
				fset.setCaseSensitive(dirset.getCasesensitive());
			}

			list.add(fset);

			include = items[i].getInclude();
			if (include != null) {
				fset.setFilterType(FileSet.FILTER_INCLUDE_DIRECTORIES);
				filter = include.getName();
				fset.setFilter(filter);
			}

			exclude = items[i].getExclude();
			if (exclude != null) {

				fset.setFilterType(FileSet.FILTER_EXCLUDE_DIRECTORIES);
				filter = exclude.getName();
				fset.setFilter(filter);
			}

		}

		return list;
	}

	public static Collection<? extends FileSet> toFileSetList(
			Packageset packageset, File wdir) {

		if (packageset == null) {
			throw new IllegalArgumentException(
					"The argument packageset may not be null!");
		}

		if (wdir == null) {
			throw new IllegalArgumentException(
					"The argument wdir may not be null!");
		}

		logger.debug("Working directory = " + wdir.getAbsolutePath());

		ArrayList<FileSet> list = new ArrayList<FileSet>();

		PackagesetItem[] items = packageset.getPackagesetItem();
		FileSet fset;

		if (items.length == 0) {

			fset = new FileSet(wdir, packageset.getDir());
			list.add(fset);
			return list;
		}

		Include include;
		Exclude exclude;
		String filter;

		for (int i = 0; i < items.length; i++) {

			fset = new FileSet(wdir, packageset.getDir());

			if (packageset.hasCasesensitive()) {
				fset.setCaseSensitive(packageset.getCasesensitive());
			}

			list.add(fset);

			include = items[i].getInclude();
			if (include != null) {
				fset.setFilterType(FileSet.FILTER_INCLUDE_PACKAGES);
				filter = include.getName();
				filter = StringServices.replace(filter, "/", ".");
				filter = StringServices.replace(filter, "\\", ".");
				fset.setFilter(filter);
			}

			exclude = items[i].getExclude();
			if (exclude != null) {
				fset.setFilterType(FileSet.FILTER_EXCLUDE_PACKAGES);
				filter = exclude.getName();
				filter = StringServices.replace(filter, "/", ".");
				filter = StringServices.replace(filter, "\\", ".");
				fset.setFilter(filter);
			}

		}

		return list;
	}
}
/*
 * $Log$
 */
