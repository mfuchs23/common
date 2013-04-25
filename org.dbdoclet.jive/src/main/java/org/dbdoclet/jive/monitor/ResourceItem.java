package org.dbdoclet.jive.monitor;

public class ResourceItem extends ModelItem {

	private Boolean hasChanged;
	private final String key;
	private final String label;
	private String value;

	public ResourceItem(String key, String label) {
		
		if (key == null) {
			throw new IllegalArgumentException("The argument key must not be null!");
		}
		
		this.key = key;
		this.label = label;
	}

	@Override
	public boolean equals(Object other) {
		return equals((ResourceItem) other);
	}

	public boolean equals(ResourceItem other) {
		return key.equals(other.getKey());
	}

	@Override
	public String getName() {
		return " " + label;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public Boolean hasChanged() {
		return hasChanged;
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	private String getKey() {
		return key;
	}

	@Override
	public void setValue(Object value) {
		
		if (value == null) {
			return;
		}
		
		Integer length = new Integer(value.toString());
		this.value = createByteUnit(length);
	}
}
