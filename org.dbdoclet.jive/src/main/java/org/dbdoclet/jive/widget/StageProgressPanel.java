/*
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.widget;

import java.awt.Dimension;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.CanceledException;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveExceptionHandler;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.text.TextServices;
import org.dbdoclet.progress.ProgressEvent;
import org.dbdoclet.progress.StageProgressListener;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;

public class StageProgressPanel extends GridPanel implements
		StageProgressListener {

	class FinishedEventHandler implements Runnable {

		private final String text;

		public FinishedEventHandler(String text) {
			this.text = text;
		}

		public void run() {

			currentStage = 0;
			stageCount = 1;

			resetStage();

			stepBar.setValue(stepBar.getMaximum());
			stepBar.setIndeterminate(false);
			mainBar.setValue(mainBar.getMaximum());
			mainBar.setIndeterminate(false);

			if (text == null) {
				setText("<html><h3>"
						+ ResourceServices.getString(res, "C_FINISHED"));
			} else {
				setText(text);
			}
		}
	}

	class ProgressEventHandler implements Runnable {

		private final ProgressEvent event;

		public ProgressEventHandler(ProgressEvent event) {
			this.event = event;
		}

		public void run() {

			int stage = event.getStage();

			String action = event.getAction();

			if (action != null) {

				action = action.trim();

				if (action.startsWith("<html>")) {
					action = StringServices.cutPrefix(action, "<html>");
					action = StringServices.cutSuffix(action, "</html>");
				} else {
					action = "<p>" + StringServices.shorten(action, 80)
							+ "</p>";
				}
			}

			event.getUserObject();

			StringBuffer buffer;

			if (stage == ProgressEvent.STAGE_PREPARE) {

				stepBar.setIndeterminate(true);
				stepBar.setStringPainted(false);

				buffer = new StringBuffer();
				buffer.append("<html><h3>");

				if (title == null) {
					buffer.append(ResourceServices.getString(res, "C_PREPARE"));
				} else {
					buffer.append(title);
				}

				buffer.append("</h3><table width='100%'><tr><th width='25%'>");
				buffer.append(ResourceServices.getString(res, "C_STAGE"));
				buffer.append("</th><th width='25%'></th><th width='25%'>");
				buffer.append(ResourceServices.getString(res, "C_START_TIME"));
				buffer.append("</th><th width='25%'>");
				buffer.append(ResourceServices.getString(res, "C_ELAPSED_TIME"));
				buffer.append("</th><th width='25%'>");
				buffer.append("");
				buffer.append("</th></tr><tr><td>");
				buffer.append(currentStage);
				buffer.append('/');
				buffer.append(stageCount);
				buffer.append("</td><td>");
				buffer.append(prepareCounter);
				buffer.append("</td><td>");
				buffer.append(df.format(new Date(progressStartTime)));
				buffer.append("</td><td>");
				buffer.append(formatElapsedTime());
				buffer.append("</td></tr></table>");

				if (action != null && action.length() > 0) {
					buffer.append(action);
				}

				buffer.append("</html>");
				setText(buffer.toString());
			}

			if (stage == ProgressEvent.STAGE_ACTION) {

				stepBar.setIndeterminate(false);
				stepBar.setStringPainted(true);

				if (event.getConsider()) {
					stepBar.setValue(progressValue);
				}

				buffer = new StringBuffer();
				buffer.append("<html><h3>");

				if (title == null) {
					buffer.append(ResourceServices
							.getString(res, "C_EXECUTING"));
				} else {
					buffer.append(title);
				}

				buffer.append("</h3><table width='100%'><tr><th width='25%'>");
				buffer.append(ResourceServices.getString(res, "C_STAGE"));
				buffer.append("</th><th width='25%'></th><th width='25%'>");
				buffer.append(ResourceServices.getString(res, "C_START_TIME"));
				buffer.append("</th><th width='25%'>");
				buffer.append(ResourceServices.getString(res, "C_ELAPSED_TIME"));
				buffer.append("</th><th width='25%'>");
				buffer.append(ResourceServices.getString(res,
						"C_ESTIMATED_TIME"));
				buffer.append("</th></tr><tr><td>");
				buffer.append(currentStage);
				buffer.append('/');
				buffer.append(stageCount);
				buffer.append("</td><td>");
				buffer.append(progressValue);
				buffer.append('/');
				buffer.append(progressMaximum);
				buffer.append("</td><td>");
				buffer.append(df.format(new Date(progressStartTime)));
				buffer.append("</td><td>");
				buffer.append(formatElapsedTime());
				buffer.append("</td><td>");
				buffer.append(formatEstimatedTime());
				buffer.append("</td></tr></table>");

				if (action != null && action.length() > 0) {
					buffer.append(action);
				}

				buffer.append("</html>");
				setText(buffer.toString());
			}
		}
	}

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(StageProgressPanel.class);
	private DateFormat df;
	private JEditorPane info;
	private JProgressBar mainBar;
	private JProgressBar stepBar;
	private ResourceBundle res;
	private String title;
	private URL bgImgUrl;
	private JiveFactory wm;
	private int currentStage = 0;
	private int prepareCounter;
	private int progressMaximum = 1;
	private int progressValue;
	private int stageCount = 1;
	private long progressStartTime = 0;
	private long lastUpdate;

	public StageProgressPanel(Frame parent, String title) {
		init(parent, title);
		lastUpdate = System.currentTimeMillis();
	}

	public void finished(String text) {

		FinishedEventHandler handler = new FinishedEventHandler(text);
		execute(handler);
	}

	public final int getProgressMaximum() {
		return progressMaximum;
	}

	public final long getProgressStartTime() {
		return progressStartTime;
	}

	public final boolean isCanceled() {
		return false;
	}

	public void nextStage() {

		logger.debug("nextStep");

		mainBar.setValue(currentStage);
		currentStage++;
		resetStage();
	}

	public boolean progress(ProgressEvent event) {

		// logger.debug("event=" + event);
		ProgressEventHandler handler = new ProgressEventHandler(event);

		if (event.getStage() == ProgressEvent.STAGE_PREPARE) {
			prepareCounter++;
		}

		if (event.getStage() == ProgressEvent.STAGE_ACTION
				&& event.getConsider()) {
			progressValue++;
		}

		if (SwingUtilities.isEventDispatchThread()) {
			handler.run();
		} else {
			try {

				long now = System.currentTimeMillis();

				int i = 333;
				if (now - lastUpdate > i) {
					SwingUtilities.invokeAndWait(handler);
					lastUpdate = now;
				}

			} catch (InterruptedException oops) {
				throw new CanceledException();
			} catch (Exception oops) {
				JiveExceptionHandler.handle(oops);
			}
		}

		return true;
	}

	public void setMainIndeterminate(boolean indeterminate) {

		mainBar.setIndeterminate(indeterminate);
		mainBar.setStringPainted(!indeterminate);
	}

	public void setProgressMaximum(int progressMaximum) {

		if (progressMaximum < 1) {
			progressMaximum = 1;
		}

		logger.debug("progressMaximum = " + progressMaximum);

		this.progressMaximum = progressMaximum;
		progressValue = 0;
		stepBar.setMaximum(progressMaximum);
	}

	public void setProgressStartTime(long progressStartTime) {
		this.progressStartTime = progressStartTime;

	}

	public void setStageCount(int stepCount) {

		this.stageCount = stepCount;
		mainBar.setMaximum(stepCount);
	}

	public void setText(String msg) {
		TextServices.setText(info, msg, bgImgUrl);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public final boolean veto(final ProgressEvent progressEvent) {
		return false;
	}

	private void execute(Runnable handler) {

		if (SwingUtilities.isEventDispatchThread()) {
			handler.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(handler);
			} catch (InterruptedException oops) {
				JiveExceptionHandler.handle(oops);
			} catch (InvocationTargetException oops) {
				JiveExceptionHandler.handle(oops);
			}
		}
	}

	private String formatElapsedTime() {

		long now = System.currentTimeMillis();
		long elapsed = now - progressStartTime;
		elapsed /= 1000;

		long eh = elapsed / 3600;
		elapsed = elapsed % 3600;

		long em = elapsed / 60;
		long es = elapsed % 60;

		StringBuffer buffer = new StringBuffer();

		buffer.append(eh);
		buffer.append(':');

		if (em < 10) {
			buffer.append('0');
		}

		buffer.append(em);
		buffer.append(':');

		if (es < 10) {
			buffer.append('0');
		}

		buffer.append(es);

		return buffer.toString();
	}

	private String formatEstimatedTime() {

		int max = progressMaximum * stageCount;
		int current = (progressMaximum * currentStage) + progressValue;

		long now = System.currentTimeMillis();
		long elapsed = now - progressStartTime;

		long time = 0;

		if (current > 0) {
			time = (elapsed / current) * max;
		}

		time = progressStartTime + time;
		return df.format(new Date(time));
	}

	private void init(Frame parent, String title) {

		wm = JiveFactory.getInstance();
		res = wm.getResourceBundle();
		bgImgUrl = wm.getBackgroundImageUrl();

		df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,
				res.getLocale());

		prepareCounter = 0;
		progressStartTime = System.currentTimeMillis();

		info = new JEditorPane();
		info.setPreferredSize(new Dimension(600, 200));
		info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// info.setBackground(Color.white);
		info.setEditable(false);
		info.setFocusable(false);
		addComponent(info, Anchor.NORTHWEST, Fill.BOTH);
		incrRow();

		stepBar = new JProgressBar();
		stepBar.setIndeterminate(false);
		stepBar.setStringPainted(true);

		addComponent(stepBar, Anchor.NORTHWEST, Fill.HORIZONTAL);
		incrRow();

		mainBar = new JProgressBar();
		mainBar.setIndeterminate(false);
		mainBar.setStringPainted(true);

		addComponent(mainBar, Anchor.NORTHWEST, Fill.HORIZONTAL);
		incrRow();
	}

	private void resetStage() {

		logger.debug("reset");

		prepareCounter = 0;
		progressMaximum = 1;
		progressValue = 0;

		stepBar.setMaximum(100);
		stepBar.setValue(0);
	}
}
