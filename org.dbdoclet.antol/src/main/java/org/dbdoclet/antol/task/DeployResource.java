/* 
 * ### Copyright (C) 2007 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.antol.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

public class DeployResource extends Task {

	private static Log logger = LogFactory.getLog(DeployResource.class);

	private String resource = "";
	private String dest = "";

	@Override
	public void execute() throws BuildException {

		Project project = getProject();

		try {

			File file;
			String fileName;
			String destUrl;
			URL url;

			URLConnection conn = null;
			OutputStream outstr = null;
			FileInputStream instr = null;

			String repositoryUrl = "http://svn.unico-production.com/cgi/ode-upload.pl?res=";

			if (resource == null || resource.trim().length() == 0) {
				throw new BuildException("The attribute resource must be set!");
			}

			if (dest == null || dest.trim().length() == 0) {
				throw new BuildException("The attribute dest must be set!");
			}

			file = new File(resource);

			if (file.exists() == false) {
				throw new BuildException("Can't find resource "
						+ file.getAbsolutePath() + "!");
			}

			if (file.isFile() == false) {
				throw new BuildException("The resource "
						+ file.getAbsolutePath() + " must be a regular file!");
			}

			instr = new FileInputStream(file);

			destUrl = repositoryUrl + dest;
			url = new URL(destUrl);

			try {

				conn = url.openConnection();
				conn.setDoOutput(true);

				outstr = conn.getOutputStream();

				byte[] buffer = new byte[4096];
				int nread;
				long nwritten = 0;

				while ((nread = instr.read(buffer)) != -1) {
					outstr.write(buffer, 0, nread);
					nwritten += nread;
				}

			} catch (Exception oops) {

				logger.fatal("deploy-resource", oops);
				throw new BuildException(oops.getMessage());

			} finally {

				try {

					if (instr != null) {
						instr.close();
					}

					if (outstr != null) {
						outstr.close();
					}

				} catch (IOException ioe) {
					logger.fatal("deploy-resource", ioe);
				}
			}

		} catch (Exception oops) {
			oops.printStackTrace();
			throw new BuildException(oops);
		}
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

}
