package org.dbdoclet.commons;

import static org.junit.Assert.assertEquals;

import org.dbdoclet.service.StringServices;
import org.junit.Test;

public class StringServicesTests {

	@Test
	public void cutNullSuffix() {

		String buf1 = "Dateiname.xml";
		String buf2 = StringServices.cutSuffix(buf1, null);
		assertEquals(buf1, buf2);
	}
}
