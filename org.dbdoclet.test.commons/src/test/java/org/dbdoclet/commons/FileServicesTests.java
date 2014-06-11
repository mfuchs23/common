package org.dbdoclet.commons;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.dbdoclet.service.FileServices;
import org.junit.Test;

public class FileServicesTests {

	@Test
	public void appendFileUnix() {

		String path = FileServices.appendFileName("/home/user/", ".profile");
		assertEquals("/home/user/.profile", path);

		path = FileServices.appendFileName("/home/user", ".profile");
		assertEquals("/home/user/.profile", path);

		File dir = new File("/home/user", ".profile");
		assertEquals("/home/user/.profile", dir.getPath());

		dir = new File("/home/user/", ".profile");
		assertEquals("/home/user/.profile", dir.getPath());

		dir.toPath().forEach(x -> { System.out.println(String.format("%s:%s", x.getClass(), x)); });
		path = null;
		dir = new File(path, ".profile");
		assertEquals("/home/user/.profile", dir.getPath());
	}
}
