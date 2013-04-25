/* 
 * ### Copyright (C) 2008 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.jive.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JProgressBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.text.TextServices;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.progress.ProgressEvent;
import org.dbdoclet.progress.ProgressVetoListener;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.service.StringServices;

public class StepProgressDialog extends DataDialog implements
		ProgressVetoListener {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(StepProgressDialog.class);

	private ArrayList<ArrayList<ProgressEvent>> stepEventList;
	private DateFormat df;
	private JEditorPane info;
	private JProgressBar mainBar;
	private JProgressBar stepBar;
	private ResourceBundle res;
	private String title;
	private URL bgImgUrl;
	private JiveFactory wm;
	private int currentStep = 0;
	private int prepareCounter;
	private int progressMaximum = 1;
	private int progressValue;
	private int stepProgressMaximum = 1;
	private int stepProgressValue;
	private int stepCount = 1;
	private long progressStartTime = 0;
	private long lastProgressEventTime = 0;

	public StepProgressDialog(Frame parent, String title) {

		super(parent, title, false);
		init(parent);
	}

	public void setMainIndeterminate(boolean indeterminate) {

		mainBar.setIndeterminate(indeterminate);
		mainBar.setStringPainted(!indeterminate);
	}

	public void setText(String msg) {
		TextServices.setText(info, msg, bgImgUrl);
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<ProgressEvent> getStepEventList(int step) {

		if (stepEventList == null) {
			throw new IllegalStateException(
					"The field stepEventList must not be null!");
		}

		if (step < 0 || step >= stepEventList.size()) {
			return null;
		}

		return stepEventList.get(step);
	}

	public void setStepCount(int stepCount) {

		if (stepCount > 1) {

			this.stepCount = stepCount;

			for (int i = stepEventList.size(); i < stepCount; i++) {
				stepEventList.add(new ArrayList<ProgressEvent>());
			}
		}
	}

	public void nextStep() {

		logger.debug("nextStep");

		if (currentStep < stepCount) {
			currentStep++;
			reset();
		}
	}

	public void reset() {

		logger.debug("reset");

		prepareCounter = 0;
		progressMaximum = 1;
		progressValue = 0;
		stepProgressMaximum = 1;
		stepProgressValue = 0;

		stepBar.setMaximum(100);
		stepBar.setValue(0);

		mainBar.setMaximum(stepCount);
		mainBar.setValue(currentStep);
	}

	public boolean progress(ProgressEvent event) {

		// logger.debug("event=" + event);

		long time = System.currentTimeMillis();
		int stage = event.getStage();

		if (stage == ProgressEvent.STAGE_PREPARE) {
			prepareCounter++;
		}

		if (stage == ProgressEvent.STAGE_ACTION && event.getConsider()) {
			progressIncr();
		}

		if (time - lastProgressEventTime < 250) {
			return true;
		}

		lastProgressEventTime = time;

		String action = event.getAction();

		if (action != null) {

			action = action.trim();

			if (action.startsWith("<html>")) {
				action = StringServices.cutPrefix(action, "<html>");
				action = StringServices.cutSuffix(action, "</html>");
			} else {
				action = "<p>" + StringServices.shorten(action, 80) + "</p>";
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

			buffer.append("</h3><table width='100%'><tr><th width='25%'></th><th width='25%'>");
			buffer.append(ResourceServices.getString(res, "C_START_TIME"));
			buffer.append("</th><th width='25%'>");
			buffer.append(ResourceServices.getString(res, "C_ELAPSED_TIME"));
			buffer.append("</th><th width='25%'>");
			buffer.append("");
			buffer.append("</th></tr><tr><td>");
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
				mainBar.setValue((stepProgressMaximum * currentStep)
						+ stepProgressValue);

				if (event.getUserObject() != null) {

					ArrayList<ProgressEvent> eventList = stepEventList
							.get(currentStep);

					if (eventList == null) {
						eventList = new ArrayList<ProgressEvent>();
						stepEventList.add(currentStep, eventList);
					}

					eventList.add(event);
				}
			}

			buffer = new StringBuffer();
			buffer.append("<html><h3>");

			if (title == null) {
				buffer.append(ResourceServices.getString(res, "C_EXECUTING"));
			} else {
				buffer.append(title);
			}

			buffer.append("</h3><table width='100%'><tr><th width='25%'></th><th width='25%'>");
			buffer.append(ResourceServices.getString(res, "C_START_TIME"));
			buffer.append("</th><th width='25%'>");
			buffer.append(ResourceServices.getString(res, "C_ELAPSED_TIME"));
			buffer.append("</th><th width='25%'>");
			buffer.append(ResourceServices.getString(res, "C_ESTIMATED_TIME"));
			buffer.append("</th></tr><tr><td>");
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

		return true;
	}

	public final boolean veto(final ProgressEvent progressEvent) {
		return false;
	}

	@Override
	public final boolean isCanceled() {
		return false;
	}

	public void setProgressStartTime(long progressStartTime) {
		this.progressStartTime = progressStartTime;

	}

	public final long getProgressStartTime() {
		return progressStartTime;
	}

	public void setProgressMaximum(int progressMaximum) {

		if (progressMaximum < 1) {
			progressMaximum = 1;
		}

		logger.debug("progressMaximum = " + progressMaximum);

		this.progressMaximum = progressMaximum;
		progressValue = 0;

		stepProgressMaximum += progressMaximum;

		stepBar.setMaximum(progressMaximum);
		mainBar.setMaximum(stepProgressMaximum * stepCount);
	}

	public final int getProgressMaximum() {
		return progressMaximum;
	}

	public final int progressIncr() {

		stepProgressValue++;
		return progressValue++;
	}

	private void init(Frame parent) {

		wm = JiveFactory.getInstance();
		res = wm.getResourceBundle();
		bgImgUrl = wm.getBackgroundImageUrl();

		df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,
				res.getLocale());
		stepEventList = new ArrayList<ArrayList<ProgressEvent>>();
		stepEventList.add(new ArrayList<ProgressEvent>());

		prepareCounter = 0;
		progressStartTime = System.currentTimeMillis();

		GridPanel panel = getGridPanel();

		info = new JEditorPane();
		info.setPreferredSize(new Dimension(600, 200));
		info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// info.setBackground(Color.white);
		info.setEditable(false);
		info.setFocusable(false);
		panel.addComponent(info, Anchor.NORTHWEST, Fill.BOTH);
		panel.incrRow();

		stepBar = new JProgressBar();
		stepBar.setIndeterminate(true);
		stepBar.setStringPainted(true);

		panel.addComponent(stepBar, Anchor.NORTHWEST, Fill.HORIZONTAL);
		panel.incrRow();

		mainBar = new JProgressBar();
		mainBar.setStringPainted(true);
		mainBar.setIndeterminate(true);

		if (stepCount > 1) {
			panel.addComponent(mainBar, Anchor.NORTHWEST, Fill.HORIZONTAL);
			panel.incrRow();
		}

		pack();
		center(parent);
	}

	private String formatEstimatedTime() {

		int max = stepProgressMaximum * stepCount;
		int current = (stepProgressMaximum * currentStep) + progressValue;

		long now = System.currentTimeMillis();
		long elapsed = now - progressStartTime;

		long time = 0;

		if (current > 0) {
			time = (elapsed / current) * max;
		}

		time = progressStartTime + time;
		return df.format(new Date(time));
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
}
