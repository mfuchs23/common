package org.dbdoclet.jive.monitor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;

public class MonitorPanel extends GridPanel {

	private static final long serialVersionUID = 1L;
	private static final int COLUMN_NAME_INDEX = 0;
	private static final int COLUMN_VALUE_INDEX = 1;
	private static final int COLUMN_ICON_INDEX = 2;

	private JTable table;
	private MonitorModel model;
	private final RefreshThread refresh;
	private ServerThread server;

	public MonitorPanel() {

		createGui();

		refresh = new RefreshThread(model);
		refresh.start();

		try {
			server = new ServerThread(model);
			server.start();
		} catch (IOException oops) {
			oops.printStackTrace();
		}
	}

	public int getLocalPort() {
		return server.getLocalPort();
	}

	@Override
	protected void finalize() throws Throwable {

		refresh.halt();
		super.finalize();
	}

	private void createGui() {

		table = new JTable();
		// table.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		table.setBorder(BorderFactory.createLineBorder(Color.black));

		model = new MonitorModel();
		table.setModel(model);
		table.setFont(new Font("monospace", Font.PLAIN, 11));

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(COLUMN_NAME_INDEX).setPreferredWidth(200);
		columnModel.getColumn(COLUMN_VALUE_INDEX).setPreferredWidth(100);
		columnModel.getColumn(COLUMN_VALUE_INDEX).setCellRenderer(
				new RightTableCellRenderer());
		columnModel.getColumn(COLUMN_ICON_INDEX).setPreferredWidth(10);
		columnModel.getColumn(COLUMN_ICON_INDEX).setCellRenderer(
				new IconTableCellRenderer());

		addComponent(table, Fill.BOTH);
		setPreferredSize(new Dimension(300, 100));
	}

	public void clear() {
		model.clear();
	}

	public void addFile(File file) {
		model.addFile(file);
	}

	public void addResource(String key, String label) {
		model.addResource(key, label);
	}

}

class RefreshThread extends Thread {

	private static Log logger = LogFactory.getLog(MonitorPanel.class);

	private boolean halt = false;
	private final MonitorModel model;

	public RefreshThread(MonitorModel model) {
		this.model = model;
	}

	@Override
	public void run() {

		while (halt == false) {

			try {
				sleep(2000);
			} catch (InterruptedException oops) {
				logger.error("Refresh was interrupted!");
			}

			model.update();
		}
	}

	public void halt() {
		halt = true;
	}
}

class ServerThread extends Thread {

	private static Log logger = LogFactory.getLog(ServerThread.class);

	private boolean halt = false;
	private final MonitorModel model;

	private final ServerSocket serverSocket;

	public ServerThread(MonitorModel model) throws IOException {

		this.model = model;
		serverSocket = new ServerSocket(0);
	}

	public int getLocalPort() {

		if (serverSocket == null) {
			return 0;
		}

		return serverSocket.getLocalPort();
	}

	@Override
	public void run() {

		while (halt == false) {

			try {

				logger.debug("accept()");
				Socket socket = serverSocket.accept();
				logger.debug("getInputStream()");
				InputStream inputStream = socket.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				logger.debug("readLine()");
				String data = reader.readLine();

				while (data != null) {

					logger.debug("data=" + data);
					logger.debug("readLine()");
					data = reader.readLine();

					if (data != null) {
						String[] tokens = data.split("\\s*,\\s*");

						if (tokens.length == 2) {

							String key = tokens[0].trim();
							ModelItem item = model.findItem(key);

							if (item != null) {
								item.setValue(tokens[1].trim());
								model.update();
							}
						}
					}
				}

				logger.debug("reader.close()");
				reader.close();

			} catch (Throwable oops) {
				logger.fatal("ServerSocket/", oops);
			}
		}

		try {
			serverSocket.close();
		} catch (Throwable oops) {
			logger.fatal("ServerSocket/", oops);
		}
	}

	public void halt() {
		halt = true;
	}
}

class RightTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	protected RightTableCellRenderer() {
		setHorizontalAlignment(JLabel.RIGHT);
	}

}

class IconTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	protected IconTableCellRenderer() {
		setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex) {

		if (value instanceof Boolean && columnIndex == 2) {

			Boolean item = (Boolean) value;

			if (item == true) {

				URL iconUrl = ResourceServices.getResourceAsUrl(
						"/images/Time16.gif",
						IconTableCellRenderer.class.getClassLoader());

				if (iconUrl != null) {

					ImageIcon icon = new ImageIcon(iconUrl, "progress");
					Rectangle rect = table.getCellRect(rowIndex, columnIndex,
							false);
					icon.setImageObserver(new MonitorImageObserver(icon, table,
							rect));
					setIcon(icon);
				}

			} else {
				setIcon(null);
			}
		}

		return this;
	}
}

class MonitorImageObserver implements ImageObserver {

	private final ImageIcon icon;
	private final Rectangle rect;
	private final JComponent comp;

	MonitorImageObserver(ImageIcon icon, JComponent comp, Rectangle rect) {

		this.icon = icon;
		this.comp = comp;
		this.rect = rect;
	}

	public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) {

		if ((flags & (FRAMEBITS | ALLBITS)) != 0) {

			if (rect != null) {
				comp.repaint(rect);
			} else {
				comp.repaint();
			}

		} else {

			icon.setImageObserver(null);
		}

		return (flags & (ALLBITS | ABORT)) == 0;
	}
}
