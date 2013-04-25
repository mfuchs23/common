package org.dbdoclet.jive.test;

import java.util.Locale;

import org.dbdoclet.unit.Length;
import org.dbdoclet.unit.LengthUnit;
import org.junit.Test;


public class FoTests {

    @Test
    public void testDistanceWithPercent() {
        
        Length distance = new Length(Locale.getDefault(), 100.0, LengthUnit.PERCENT);
        distance.processValue(Locale.getDefault(), "100%");
        distance.processValue(Locale.getDefault(), "");
        distance.getUnit();
    }
}
