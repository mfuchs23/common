package org.dbdoclet.jive.test;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.dbdoclet.jive.fo.FoAttributeSet;
import org.dbdoclet.jive.fo.FoAttributeSetChooser;
import org.dbdoclet.jive.widget.GridPanel;

public class FoAttributeSetChooserTest implements ActionListener {

	private static JFrame frame;
	private FoAttributeSetChooser fontAttributeSetChooser;
	private FoAttributeSet[] asetList;

	public static void main(String[] args) {

		FoAttributeSetChooserTest app = new FoAttributeSetChooserTest();
		app.execute();
	}

	private void execute() {

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		asetList = new FoAttributeSet[5];
		asetList[0] = new FoAttributeSet("section.level1.properties",
				frame.getFont(), Color.black);
		asetList[1] = new FoAttributeSet("variablelist.term.properties",
				frame.getFont(), Color.black);
		asetList[2] = new FoAttributeSet("admonition.label.properties",
				frame.getFont(), Color.black);
		asetList[3] = new FoAttributeSet("Überschrift 4", frame.getFont(),
				Color.black);
		asetList[4] = new FoAttributeSet("list.item.spacing", frame.getFont(),
				Color.black);

		asetList[4].setType(FoAttributeSet.SPACING_SET);

		String[] fontFamilyList = new String[] { "Arial", "Courier",
				"Helvetica", "Times", "monospace", "sans-serif", "serif" };

		fontAttributeSetChooser = new FoAttributeSetChooser(asetList,
				fontFamilyList);
		// FoPropertiesList fontList = new FoPropertiesList();
		GridPanel mainPanel = new GridPanel();
		mainPanel.addComponent(new JScrollPane(fontAttributeSetChooser));
		JButton printButton = new JButton("Anzeigen");
		printButton.setActionCommand("print");
		printButton.addActionListener(this);
		mainPanel.addButton(printButton);

		JButton exitButton = new JButton("Exit");
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);
		mainPanel.addButton(exitButton);

		mainPanel.prepare();
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setVisible(true);

	}

	private void println(String text) {
		System.out.println(text);
	}

	public void actionPerformed(ActionEvent e) {

		FoAttributeSet aset = asetList[0];

		if (e.getActionCommand().equals("print")) {

			println("----------------------------------------------");
			println("isActivated: " + aset.isActivated());

			if (aset.isActivated()) {

				println("+++ FontEnabled: " + aset.isFontEnabled());

				if (aset.isFontEnabled()) {
					println("\tForeground: " + aset.getForeground());
					println(".");
				}

				println("+++ SpacingEnabled: "
						+ asetList[0].isSpacingEnabled());

				if (aset.isSpacingEnabled()) {
					println(".");
				}

				println("+++ FrameEnabled: " + asetList[0].isFrameEnabled());
				if (aset.isFrameEnabled()) {
					System.out.println("Rahmenbreite: "
							+ asetList[0].getFrameWidth());
					println(".");
				}

				println("+++ LineEnabled: " + aset.isLineEnabled());
				if (aset.isLineEnabled()) {
					System.out.println("\twrap-option: "
						+ asetList[0].getWrapOption());
					System.out.println("\twidth: " + asetList[0].getLineWidth());
				}
			}

		} else {
			frame.setVisible(false);
			frame.dispose();
		}
	}

}
