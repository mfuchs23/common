package org.dbdoclet.jive.test;

import java.util.Locale;

import javax.swing.JFileChooser;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.ContinueBox;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.jive.dialog.ImageChooser;
import org.dbdoclet.jive.dialog.InfoBox;
import org.dbdoclet.jive.dialog.ProcessBox;
import org.dbdoclet.jive.dialog.WarningBox;
import org.dbdoclet.jive.widget.HelpPanel;
import org.dbdoclet.service.StringServices;
import org.junit.Before;
import org.junit.Test;

public class DialogTests {

	@Before
	public void startUp() {
		JiveFactory.getInstance(Locale.getDefault());
	}

	@Test
	public void testHelpPanel_1() {

		JFileChooser chooser = new JFileChooser();
		chooser.setAccessory(new HelpPanel(
				"<html><p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, "
						+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>"
						+ "<p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi"
						+ " ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit"
						+ " in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur"
						+ " sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt"
						+ " mollit anim id est laborum."));
		chooser.showOpenDialog(null);
	}

	@Test
	public void testExceptionBox_1() {
	
		try {
			String text = "XXX";
			System.out.println(text.length() / 0);
			
		} catch (Throwable oops) {
			ExceptionBox ebox = new ExceptionBox(oops);
			ebox.setVisible(true);
		}
	}

	@Test
	public void testErrorBox_1() {
		ErrorBox.show(
				"Fehlermeldung",
				"<html>Lorem ipsum dolor <b>sit</b> amet, <code>consectetuer</code> adipiscing elit. Praesent fermentum lacus non nulla. Duis sit amet velit ac sapien dignissim fringilla. Proin id pede eget pede cursus pharetra. Curabitur in nisi.");
	}

	@Test
	public void testWarningBox_1() {
		WarningBox.show(
				"Warnung",
				"<html>Lorem ipsum dolor <b>sit</b> amet, <code>consectetuer</code> adipiscing elit. Praesent fermentum lacus non nulla. Duis sit amet velit ac sapien dignissim fringilla. Proin id pede eget pede cursus pharetra. Curabitur in nisi.");
	}

	@Test
	public void testContinueBox_1() {
		ContinueBox.show(
				"Weiter machen?",
				"<html><h3>Lorem ipsum</h3><p>dolor sit amet, <code>" +
				StringServices.makeWrapable("/usr/share/lib/var/dev/proc/kernel/source/und/vieles/mehr/komisch/home/tick/trick/track/donald/dagobert") +
				"</code> consectetuer adipiscing elit. " +
				"Praesent fermentum lacus non nulla. Duis sit amet velit ac sapien dignissim fringilla. " +
				"Proin id pede eget pede cursus pharetra. Curabitur in nisi.");
	}
	
	@Test
	public void testInfoBox_1() {
		InfoBox.show(
				"Information",
				"<html><h3>Lorem ipsum</h3><p> dolor sit amet, consectetuer adipiscing elit. " +
				"Praesent fermentum lacus non nulla. Duis sit amet velit ac sapien dignissim fringilla. " +
				"Proin id pede eget pede cursus pharetra. Curabitur in nisi.");
	}

	@Test
	public void testImageChooser_1() {

		ImageChooser chooser = new ImageChooser();
		chooser.showOpenDialog(null);
	}

	@Test
	public void testProcessBox_1() throws InterruptedException {

		ProcessBox pbox = new ProcessBox(null, "Test ProcessBox");
		pbox.setVisible(true);

		for (int i = 0; i < 12; i++) {

			Thread.sleep(500);
			pbox.info("Dies ist Nachricht Nummer " + (i + 1) + ".");
		}

		for (int i = 0; i < 12; i++) {

			Thread.sleep(500);
			pbox.info("Kopiere Datei /usr/share/dbdoclet/docbook/xsl/highlighting/xslthl-config.xml nach /usr/share/dbdoclet/docbook/xsl/highlighting/xslthl-config.xml.");
		}

		Thread.sleep(2000);
		pbox.setVisible(false);

	}
}
