package org.voelk.holidays.application;


import org.junit.*;

import static junit.framework.Assert.assertEquals;
import static org.voelk.holidays.util.DateConstants.*;

public class JollydayHolidayCalculatorTest {
    public static final String JOLLYDAY_PROPERTIES_FILEPATH = "d:/development/projekte/holidays/java/test/singlethread_jollyday.properties";
    private JollydayHolidayCalculator holidayCalculator = new JollydayHolidayCalculator();


    @Before
    public void setUp() throws Exception {
        System.setProperty("de.jollyday.config", JOLLYDAY_PROPERTIES_FILEPATH);
    }

    @Test
    public void testOneDay() throws Exception {
        org.voelk.holidays.entities.Period period = new org.voelk.holidays.entities.Period(FRIDAY, FRIDAY);
        assertEquals(1.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testTwoDays() throws Exception {
        org.voelk.holidays.entities.Period period = new org.voelk.holidays.entities.Period(THURSDAY, FRIDAY);
        assertEquals(2.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testFridayToMonday() throws Exception {
        org.voelk.holidays.entities.Period period = new org.voelk.holidays.entities.Period(FRIDAY, MONDAY);
        assertEquals(2.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testMonthWith21WorkDays() throws Exception {
        org.voelk.holidays.entities.Period period = new org.voelk.holidays.entities.Period(JANUARY_1ST_2012, JANUARY_31ST_2012);
        assertEquals(21.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testHoliday() throws Exception {
        org.voelk.holidays.entities.Period period = new org.voelk.holidays.entities.Period(MONDAY_BOXING_DAY, MONDAY_BOXING_DAY);
        assertEquals(0.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testHalfDayOnWeekend() throws Exception {
        org.voelk.holidays.entities.Period period = new org.voelk.holidays.entities.Period(CHRISTMAS_2011, CHRISTMAS_2011);
        assertEquals(0.0, holidayCalculator.calculateWorkingDays(period));
        period = new org.voelk.holidays.entities.Period(NEW_YEARS_EVE_2011, NEW_YEARS_EVE_2011);
        assertEquals(0.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testHalfDay() throws Exception {
        org.voelk.holidays.entities.Period period = new org.voelk.holidays.entities.Period(CHRISTMAS_2012, CHRISTMAS_2012);
        assertEquals(0.5, holidayCalculator.calculateWorkingDays(period));
        period = new org.voelk.holidays.entities.Period(NEW_YEARS_EVE_2012, NEW_YEARS_EVE_2012);
        assertEquals(0.5, holidayCalculator.calculateWorkingDays(period));
    }


    @Test
    public void testBetweenYears() throws Exception {
        org.voelk.holidays.entities.Period period = new org.voelk.holidays.entities.Period(BETWEEN_YEARS_START, BETWEEN_YEARS_END);
        assertEquals(13.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testHolidaysForBavariaIn2012() throws Exception {
        assertEquals(13, holidayCalculator.getHolidaysFor(2012).size());
    }

    @Test
    public void testHalfDayHolidaysForLa2() throws Exception {
        assertEquals(2, holidayCalculator.getHalfDayHolidaysFor(2012).size());
    }
}
