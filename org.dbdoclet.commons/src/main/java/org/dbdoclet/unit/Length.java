package org.dbdoclet.unit;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.service.StringServices;

/**
 * Längenangabe mit zugehöriger Einheit.
 * 
 * @author michael
 */
public class Length {

	private static final LengthUnit DEFAULT_UNIT = LengthUnit.MILLIMETER;

	private static Log logger = LogFactory.getLog(Length.class);

	private Double distance = null;
	private boolean excludePercent = true;
	private Locale locale = Locale.getDefault();
	private LengthUnit unit = LengthUnit.MILLIMETER;

	public Length() {
		this(Locale.getDefault(), null, null);
	}

	public Length(Double distance) {
		this(Locale.getDefault(), distance, DEFAULT_UNIT);
	}

	public Length(Double distance, LengthUnit unit) {
		this(Locale.getDefault(), distance, unit);
	}

	public Length(float distance, LengthUnit unit) {
		this(Locale.getDefault(), Double.valueOf(distance), unit);
	}

	public Length(int top) {
		this(Locale.getDefault(), Double.valueOf(top), DEFAULT_UNIT);
	}

	public Length(Locale locale, Double distance, LengthUnit unit) {

		this.setLocale(locale);
		this.distance = distance;
		this.unit = unit;
	}

	public static double toMillimeter(Object value) {

		if (value instanceof Length == false) {
			throw new IllegalArgumentException(
					"The argument value must be of type Distance!");
		}

		Length length = (Length) value;

		if (length.getUnit() == LengthUnit.POINT) {
			return (length.getLength() / 72.0f) * 25.4f;
		}

		if (length.getUnit() == LengthUnit.INCH) {
			return length.getLength() * 25.4f;
		}

		if (length.getUnit() == LengthUnit.CENTIMETER) {
			return length.getLength() * 10;
		}

		return length.getLength();
	}

	public static Length valueOf(String value) {
		return valueOf(value, true);
	}

	public static Length valueOf(String value, boolean excludePercent) {

		Length distance = new Length(Locale.US, null, DEFAULT_UNIT);
		distance.setExcludePercent(excludePercent);

		if (distance.processValue(Locale.US, value) == false) {
			return null;
		}

		return distance;
	}

	public void decrDistance(float decr) {

		if (distance != null) {
			distance -= decr;
		}
	}

	public Length deepCopy() {

		if (locale == null) {
			locale = Locale.getDefault();
		}

		if (unit == null) {
			unit = LengthUnit.MILLIMETER;
		}

		if (distance == null) {
			return new Length(locale, null, unit);
		}

		Length copy = new Length(locale, Double.valueOf(distance), unit);
		return copy;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		Length other = (Length) obj;

		if (distance == null && other.distance != null) {
			return false;
		}

		if (distance == null && other.distance == null) {
			return false;
		}

		if (distance.equals(other.distance) == false) {
			return false;
		}

		if (unit == null && other.unit != null) {
			return false;
		}

		if (unit.equals(other.unit) == false) {
			return false;
		}

		return true;
	}

	public boolean excludePercent() {
		return excludePercent;
	}

	public float getLength() {

		if (distance == null) {
			return 0;
		}

		return distance.floatValue();
	}

	public Locale getLocale() {
		return locale;
	}

	public LengthUnit getUnit() {
		return unit;
	}

	@Override
	public int hashCode() {

		final int prime = 31;

		int result = 1;
		result = prime * result
				+ ((distance == null) ? 0 : distance.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());

		return result;
	}

	public void incrDistance(float incr) {

		if (distance != null) {
			distance += incr;
		}
	}

	public Length minus(Length operand) {

		if (operand == null) {
			return this.deepCopy();
		}

		double sum = this.toMillimeter() - operand.toMillimeter();
		return new Length(sum, LengthUnit.MILLIMETER);
	}

	public Length plus(Length operand) {

		if (operand == null) {
			return this.deepCopy();
		}

		double sum = this.toMillimeter() + operand.toMillimeter();
		return new Length(sum, LengthUnit.MILLIMETER);
	}

	public boolean processValue(Locale locale, String value) {

		if (value == null || value.trim().length() == 0) {

			unit = null;
			distance = null;
			return false;
		}

		String tu = null;
		double td;

		value = value.trim().toLowerCase();

		for (LengthUnit unit : LengthUnit.values()) {

			if (excludePercent() == true && unit.equals(LengthUnit.PERCENT)) {
				continue;
			}

			if (value.endsWith(unit.getAbbreviation())) {
				tu = unit.getAbbreviation();
				break;
			}
		}

		if (tu == null) {
			tu = LengthUnit.POINT.getAbbreviation();
		}

		value = StringServices.cutSuffix(value, tu);

		try {

			NumberFormat formatter = NumberFormat.getInstance(locale);
			Number num = formatter.parse(value);
			td = num.doubleValue();

		} catch (Throwable oops) {

			logger.error("Invalid value " + value + " for length.", oops);
			return false;
		}

		unit = LengthUnit.valueOfAbbrev(tu);
		distance = td;

		return true;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void setExcludePercent(boolean excludePercent) {
		this.excludePercent = excludePercent;
	}

	public void setLength(double length) {
		this.distance = length;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setLocalizedValue(String value) {
		processValue(getLocale(), value);
	}

	public void setUnit(LengthUnit unit) {
		this.unit = unit;
	}

	public double toMillimeter() {
		return toMillimeter(this);
	}

	public String toNormalizedString() {

		if (distance == null) {
			return null;
		}

		if (unit == LengthUnit.PERCENT) {
			return String.format(Locale.US, "%.0f%s", distance,
					unit.getAbbreviation());
		}

		return String.format(Locale.US, "%.2f%s", distance,
				unit.getAbbreviation());
	}

	public double toPoint() {

		double millis = toMillimeter();
		return millis * 2.834645669;
	}

	@Override
	public String toString() {

		if (distance == null) {
			return null;
		}

		return String.format(getLocale(), "%.2f %s", distance,
				unit.getAbbreviation());
	}
}
