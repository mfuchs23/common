package org.dbdoclet.jive.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.text.TextServices;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;

public class OverwriteDialog extends AbstractDialog {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(OverwriteDialog.class);

	public final static int SINGLE = 1;
	public final static int MULTIPLE = 2;

	public final static int YES = 1;
	public final static int YES_FOR_ALL = 2;
	public final static int NO = 3;
	public final static int NO_FOR_ALL = 4;

	private static final int WIDTH = 700;

	private JEditorPane info;
	private ResourceBundle res;
	private URL backgroundImageUrl;
	private int status = NO;
	private int type = MULTIPLE;

	public OverwriteDialog(final Frame parent, final String title,
			final ImageIcon icon, final int type)

	throws IOException {

		super(parent, title, true);

		this.type = type;

		try {

			if (SwingUtilities.isEventDispatchThread()) {

				init(parent, title, icon);

			} else {

				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						try {

							init(parent, title, icon);

						} catch (Exception oops) {
							logger.fatal("OverwriteDialog.OverwriteDialog",
									oops);
						}

					}
				});
			}

		} catch (Exception oops) {
			logger.fatal("OverwriteDialog.OverwriteDialog", oops);
		}
	}

	public void setMessage(String msg) {

		TextServices.setText(info, msg, backgroundImageUrl);
	}

	private void init(Window parent, String title, ImageIcon icon)
			throws IOException {

		JiveFactory wm = JiveFactory.getInstance();
		res = wm.getResourceBundle();

		backgroundImageUrl = wm.getBackgroundImageUrl();

		Font font;

		GridPanel panel = new GridPanel(new Insets(0, 0, 0, 0));
		panel.setBorder(BorderFactory.createEtchedBorder());

		getContentPane().add(panel);

		JLabel background = new JLabel(icon);
		JLabel heading = new JLabel(title);

		font = heading.getFont();
		font = font.deriveFont(18.0F);

		heading.setFont(font);
		heading.setForeground(Color.white);

		JLayeredPane headerPane = new JLayeredPane();

		headerPane.setOpaque(true);
		headerPane.setBackground(Color.white);

		headerPane.setPreferredSize(new Dimension(WIDTH, 50));
		headerPane.setMinimumSize(new Dimension(WIDTH, 50));
		headerPane.setMaximumSize(new Dimension(WIDTH, 50));

		headerPane.add(background, new Integer(Integer.MIN_VALUE));
		headerPane.add(heading, new Integer(100));

		background.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		heading.setBounds(20, 0, WIDTH, 50);

		panel.addComponent(headerPane, Anchor.NORTHWEST, Fill.HORIZONTAL);
		panel.incrRow();

		info = new JEditorPane();
		info.setPreferredSize(new Dimension(600, 200));
		info.setBackground(Color.white);
		info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		info.setBackground(Color.white);
		info.setEditable(false);
		info.setFocusable(false);

		JScrollPane scrollPane = new JScrollPane(info);
		scrollPane.setBackground(Color.white);

		panel.addComponent(scrollPane, Anchor.NORTHWEST, Fill.BOTH);
		panel.incrRow();

		GridPanel buttonPane = new GridPanel();
		buttonPane.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		JButton yesButton = new JButton(
				ResourceServices.getString(res, "C_YES"));

		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status = YES;
				setVisible(false);
				dispose();
			}
		});

		JButton yesForAllButton = null;

		if (type == MULTIPLE) {

			yesForAllButton = new JButton(ResourceServices.getString(res,
					"C_YES_FOR_ALL"));
			yesForAllButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					status = YES_FOR_ALL;
					setVisible(false);
					dispose();
				}
			});
		}

		JButton noButton = new JButton(ResourceServices.getString(res, "C_NO"));
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status = NO;
				setVisible(false);
				dispose();
			}
		});

		JButton noForAllButton = null;

		if (type == MULTIPLE) {

			noForAllButton = new JButton(ResourceServices.getString(res,
					"C_NO_FOR_ALL"));
			noForAllButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					status = NO_FOR_ALL;
					setVisible(false);
					dispose();
				}
			});
		}

		buttonPane.addComponent(yesButton, Anchor.CENTER);

		if (type == MULTIPLE) {
			buttonPane.addComponent(yesForAllButton, Anchor.CENTER);
		}

		buttonPane.addComponent(noButton, Anchor.CENTER);

		if (type == MULTIPLE) {
			buttonPane.addComponent(noForAllButton, Anchor.CENTER);
		}

		panel.addComponent(buttonPane, Anchor.CENTER, Fill.HORIZONTAL);
	}

	public int getStatus() {
		return status;
	}
}
