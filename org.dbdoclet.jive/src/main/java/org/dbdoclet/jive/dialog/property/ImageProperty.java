/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog.property;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.dbdoclet.jive.dialog.ImageChooser;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.StringServices;
import org.dbdoclet.xiphias.ImageServices;

public class ImageProperty extends AbstractProperty {

	/**
	 * Das Basisverzeichnis, in dem nach Bilddateien gesucht wird.
	 */
	private File baseDir;

	private JFileChooser chooser;
	private JLabel label;

	private String[] supportedExtensions = new String[] { "gif", "jpg", "png" };
	
	public ImageProperty(String label, File value) {
		super(label, value);
		chooser = new ImageChooser();
	}

	public File getBaseDir() {
		return baseDir;
	}

	@Override
	public Component getEditor(Object value) {

		label = new JLabel();

		if (getBaseDir() != null) {
			chooser.setCurrentDirectory(getBaseDir());
		}

		label.setFont(getPlainFont());
		label.setName("imageType://" + getImagePath());
		label.setEnabled(isEnabled());
		
		if (isRenderable()) {

			ImageIcon icon = ImageServices.getScaledIcon(new ImageIcon(
					getRenderableImagePath()), -1, 20);

			label.setText("");
			label.setIcon(icon);
			label.setHorizontalAlignment(SwingUtilities.CENTER);

		} else {

			label.setText(getImagePath());
			label.setIcon(null);
		}

		if (isEnabled() == true) {
		
			int rc = chooser.showOpenDialog(getPanel());

			if (rc == JFileChooser.APPROVE_OPTION) {

				File imageFile = chooser.getSelectedFile();

				if (isRenderable(imageFile)) {

					ImageIcon icon = ImageServices.getScaledIcon(new ImageIcon(
						getRenderableImagePath(imageFile)), -1, 20);

					label.setText("");
					label.setIcon(icon);				
					label.setHorizontalAlignment(SwingUtilities.CENTER);

				} else {

					label.setText(imageFile.getPath());
					label.setIcon(null);
				}

				if (getAction() != null) {
					getAction().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
				}

				label.setName("imageType://" + imageFile.getAbsolutePath());
			}
		
		}
		
		return label;
	}

	@Override
	public Object getEditorValue() {

		if (label == null) {
			throw new IllegalStateException("The field label must not be null!");
		}

		String name = label.getName();
		if (name != null && name.startsWith("imageType://")) {
			return StringServices.cutPrefix(name, "imageType://");
		}

		return "";
	}

	public File getImageFile() {

		Object obj = getValue();

		if (obj == null) {
			return null;
		}

		if (obj instanceof File) {
			return (File) obj;
		}

		if (obj instanceof String) {

			String str = (String) obj;

			if (str.trim().length() == 0) {
				return null;
			}

			return new File(str);
		}

		throw new IllegalStateException(
				"The field value must be of type File or String, not "
						+ obj.getClass().getName() + "!");
	}

	public String getImagePath() {

		File imageFile = getImageFile();

		if (imageFile != null) {
			return imageFile.getAbsolutePath();
		} else {
			return "";
		}
	}

	public File getRenderableImageFile(File imageFile) {

		String ext = FileServices.getExtension(imageFile.getAbsolutePath())
				.toLowerCase();

		if (Arrays.asList(supportedExtensions).contains(ext) == true) {
			return imageFile;
		}

		String path = FileServices.getFileBase(imageFile);
		String renderableImagePath;
		File renderableImageFile;

		for (int i = 0; i < supportedExtensions.length; i++) {

			renderableImagePath = path + "." + supportedExtensions[i];
			renderableImageFile = new File(renderableImagePath);

			if (renderableImageFile.exists() == true) {
				return renderableImageFile;
			}
		}

		return null;
	}

	public String getRenderableImagePath() {
		return getRenderableImagePath(getImageFile());
	}

	public String getRenderableImagePath(File imageFile) {

		File file = getRenderableImageFile(imageFile);

		if (file != null && file.exists()) {
			return file.getAbsolutePath();
		}

		return "";
	}

	@Override
	public Component getRenderer(Object value) {

		File imageFile = getImageFile();

		JLabel label = new JLabel();
		label.setFont(getPlainFont());

		ImageIcon icon = null;

		if (isRenderable()) {

			if (imageFile != null && imageFile.exists()) {
				icon = ImageServices.getScaledIcon(new ImageIcon(
						getRenderableImagePath()), -1, 20);
			}

			label.setText("");

			if (icon != null) {
				label.setIcon(icon);
			}

		} else {

			label.setIcon(null);

			if (imageFile != null && imageFile.exists()) {
				label.setText(imageFile.getAbsolutePath());
			} else {
				label.setText("");
			}
		}

		return label;
	}

	@Override
	public int getType() {
		return TYPE_IMAGE;
	}

	public boolean isRenderable() {
		return isRenderable(getImageFile());
	}

	public boolean isRenderable(File imageFile) {

		if (imageFile == null || imageFile.exists() == false) {
			return false;
		} else {

			if (getRenderableImageFile(imageFile) != null) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Setzt das Basisverzeichnis, in dem nach Bilddateien gesucht wird.
	 * 
	 * @param baseDir
	 *            <code>File</code> Das Basisverzeichnis.
	 */
	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}

	/**
	 * Setzt das Dialogobjekt, das zur Auswahl des Bildes verwendet werden soll.
	 * 
	 * @param chooser
	 */
	public void setChooser(JFileChooser chooser) {
		
		if (chooser != null) {
			this.chooser = chooser;
		}
	}

	public JFileChooser getChooser() {
		return chooser;
	}
}
