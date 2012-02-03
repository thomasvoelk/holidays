package org.voelk.holidays.learning;

import org.joda.time.*;
import org.joda.time.chrono.GregorianChronology;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class JodaLearningTest {

    private static final LocalDate BETWEEN_YEARS_START = new LocalDate(2011, 12, 25, GregorianChronology.getInstance());
    private static final LocalDate BETWEEN_YEARS_END = new LocalDate(2012, 1, 1, GregorianChronology.getInstance());

    @Test
    public void testPeriod() throws Exception {
        Days d = Days.daysBetween(BETWEEN_YEARS_START, BETWEEN_YEARS_END);
        assertEquals(7, d.getDays());
    }


}
