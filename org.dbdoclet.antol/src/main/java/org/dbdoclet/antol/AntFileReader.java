package org.dbdoclet.antol;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.dbdoclet.antol.ant.AntProject;
import org.dbdoclet.antol.ant.AntProjectItem;
import org.dbdoclet.antol.ant.Javadoc;
import org.dbdoclet.antol.ant.Path;
import org.dbdoclet.antol.ant.Property;
import org.dbdoclet.antol.ant.Target;
import org.dbdoclet.antol.ant.TargetItem;
import org.dbdoclet.xiphias.XmlServices;

/**
 * Die Klasse <code>AntFileReader</code> .
 * 
 * @author <a href="mailto:michael.fuchs@unico-group.com">Michael Fuchs</a>
 * @version 1.0
 */
public class AntFileReader {

	private AntProject project;

	public AntFileReader(File buildFile) throws IOException {

		try {
			String encoding = XmlServices.getEncoding(buildFile);
			project = AntProject.unmarshal(new InputStreamReader(
					new FileInputStream(buildFile), encoding));
		} catch (Throwable oops) {
			throw new IOException(oops.getMessage());
		}
	}

	public AntProject getAntProject() {
		return project;
	}

	public Path findPath(String refid) {

		if (project == null) {
			throw new IllegalStateException(
					"The field project may not be null!");
		}

		if (refid == null) {
			throw new IllegalArgumentException(
					"The argument refid may not be null!");
		}

		AntProjectItem[] items = project.getAntProjectItem();
		Path path;

		for (int i = 0; i < items.length; i++) {

			path = items[i].getPath();
			if (path != null && refid.equals(path.getId())) {
				return path;
			}
		}

		return null;
	}

	public String getProperty(String name) {

		if (project == null) {
			throw new IllegalStateException(
					"The field project may not be null!");
		}

		if (name == null) {
			throw new IllegalArgumentException(
					"The argument namemay not be null!");
		}

		AntProjectItem[] items = project.getAntProjectItem();
		Property property;

		for (int i = 0; i < items.length; i++) {

			property = items[i].getProperty();

			if (property != null && name.equals(property.getName())) {
				return property.getValue();
			}
		}

		return null;
	}

	public Target findTarget(String name) {

		if (project == null) {
			throw new IllegalStateException(
					"The field project may not be null!");
		}

		if (name == null) {
			throw new IllegalArgumentException(
					"The argument name may not be null!");
		}

		AntProjectItem[] items = project.getAntProjectItem();
		Target target;

		for (int i = 0; i < items.length; i++) {

			target = items[i].getTarget();

			if (target != null && name.equals(target.getName())) {
				return target;
			}
		}

		return null;
	}

	public Object getTask(Target target, Class<?> clazz) {

		if (target == null) {
			throw new IllegalArgumentException(
					"The argument target may not be null!");
		}

		if (clazz == null) {
			throw new IllegalArgumentException(
					"The argument clazz may not be null!");
		}

		TargetItem[] items = target.getTargetItem();
		Object obj;

		for (int i = 0; i < items.length; i++) {

			obj = null;

			if (clazz == Javadoc.class) {
				obj = items[i].getJavadoc();
			}

			if (obj != null) {
				return obj;
			}
		}

		return null;
	}

}
