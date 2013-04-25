package org.dbdoclet.jive.model;

import java.util.ArrayList;
import java.util.Locale;

import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.dbdoclet.unit.Length;

public class SpinnerDistanceModel implements SpinnerModel {

	private Length length;
	private ArrayList<ChangeListener> listenerList;
	private final Locale locale;
	private boolean excludePercent = true;

	public SpinnerDistanceModel(Locale locale) {

		this.locale = locale;
		length = new Length();
		
		listenerList = new ArrayList<ChangeListener>();
	}

	public void setExcludePercent(boolean excludePercent) {
		this.excludePercent = excludePercent;

		if (length != null) {
			length.setExcludePercent(excludePercent);
		}
	}

	public void addChangeListener(ChangeListener listener) {

		if (listener == null) {
			return;
		}

		if (listenerList.contains(listener) == false) {
			listenerList.add(listener);
		}
	}

	public Object getNextValue() {

		if (length != null) {
			length.incrDistance(0.1F);
		}

		return length;
	}

	public Object getPreviousValue() {

		if (length != null) {
			length.decrDistance(0.1F);
		}

		return length;
	}

	public Object getValue() {
		return length;
	}

	public void removeChangeListener(ChangeListener listener) {

		if (listener == null) {
			return;
		}

		listenerList.remove(listener);
	}

	public void setValue(Object value) {

		if (value == null) {
			
			length = new Length();
			length.setExcludePercent(excludePercent);
			length.setLocale(locale);
			
		} else if (value instanceof String) {

			length.setLocalizedValue((String) value);

		} else if (value instanceof Length) {

			length = (Length) value;
			length.setExcludePercent(excludePercent);
			length.setLocale(locale);

		} else {

			throw new IllegalArgumentException(
					"Invalid type "
							+ value.getClass().getName()
							+ " for SpinnerDistanceModel. Only String and Distance is allowed.");
		}

		fireChangeEvent();
	}

	private void fireChangeEvent() {

		for (ChangeListener listener : listenerList) {
			listener.stateChanged(new ChangeEvent(this));
		}
	}

}
