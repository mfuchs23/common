package org.dbdoclet;

/**
 * Die Klasse Identifier realisiert einen Identifikator. Der Zweck dieser Klasse
 * ist es Variablen, die einen Identifikator repräsentieren auch als solche
 * kenntlich zu machen und von Zeichenketten abzugrenzen. Dies führt .z.B. zur
 * mehr Klarheit bei den Methiodenaufrufen.
 * 
 * @author michael
 */
public class Identifier {

	private final String value;

	public Identifier(String value) {

		if (value == null) {
			throw new IllegalArgumentException(
					"Argument value must not be null!");
		}

		this.value = value;
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

		Identifier other = (Identifier) obj;

		if (value == null && other.value != null) {
			return false;
		}

		if (value != null && other.value == null) {
			return false;
		}

		if (value.equals(other.value) == false) {
			return false;
		}

		return true;
	}

	/**
	 * Liefert den aktuellen Wert des Identifikators.
	 * 
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	/**
	 * Liefert die Länge des Identifikators.
	 * 
	 * @return int
	 */
	public int length() {
		return value.length();
	}

	@Override
	public String toString() {
		return value;
	}
}
