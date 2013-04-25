package org.dbdoclet.jive.monitor;

import java.util.HashMap;

public abstract class ModelItem {

	HashMap<Integer, String> unitMap = new HashMap<Integer, String>() {
			private static final long serialVersionUID = 1L;
			{
					put(0, "Byte");
					put(1, "KiB");
					put(2, "MiB");
					put(3, "GiB");
					put(4, "TiB");
					put(5, "PiB");
			}
		};

	public abstract String getName();
	public abstract String getValue();
	public abstract Boolean hasChanged();
	public abstract void setValue(Object value);
	
	protected String createByteUnit(long length) {
		long divisor = 1024;
		
		int unitIndex = 0;
		
		while (length > divisor) {
			divisor *= 1024;
			unitIndex++;
		}
		
		divisor /= 1024;
		double size = new Double(length) / new Double(divisor);
		
		String value = String.format("%12.2f %s", size, unitMap.get(unitIndex));
		return value;
	} 
}
