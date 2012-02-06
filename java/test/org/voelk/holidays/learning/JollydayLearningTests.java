package org.voelk.holidays.learning;

import de.jollyday.*;
import de.jollyday.util.*;
import org.joda.time.*;
import org.junit.*;

import java.util.*;

import static junit.framework.Assert.assertEquals;

public class JollydayLearningTests {

    private static final LocalDate MONDAY_BOXING_DAY = CalendarUtil.convertToGregorianDate(CalendarUtil.create(2011, 12, 26));

    @Test
    public void testGermany() throws Exception {
        HolidayManager m = HolidayManager.getInstance(HolidayCalendar.GERMANY);
        Set<Holiday> holidays = m.getHolidays(2011);
        assertEquals(10, holidays.size());
    }

    @Test
    public void testBavaria() throws Exception {
        HolidayManager m = HolidayManager.getInstance(HolidayCalendar.GERMANY);
        Set<Holiday> holidays = m.getHolidays(2011, "by");
        assertEquals(13, holidays.size());
    }


    @Test
    public void testIsHoliday() throws Exception {
        HolidayManager m = HolidayManager.getInstance(HolidayCalendar.GERMANY);
        assertEquals(true, m.isHoliday(MONDAY_BOXING_DAY));
    }

}
