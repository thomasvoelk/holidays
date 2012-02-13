package org.voelk.holidays.application;

import org.joda.time.*;
import org.junit.*;

import java.util.*;

import static junit.framework.Assert.assertEquals;

public class JollydayHolidayCalculatorTest {
    public static final String JOLLYDAY_PROPERTIES_FILEPATH = "d:/development/projekte/holidays/java/test/singlethread_jollyday.properties";
    private JollydayHolidayCalculator holidayCalculator = new JollydayHolidayCalculator();
    private static final Date THURSDAY = new LocalDate(2011, 9, 8).toDate();
    private static final Date FRIDAY = new LocalDate(2011, 9, 9).toDate();
    private static final Date MONDAY = new LocalDate(2011, 9, 12).toDate();
    private static final Date MONDAY_BOXING_DAY = new LocalDate(2011, 12, 26).toDate();
    private static final Date CHRISTMAS_2011 = new LocalDate(2011, 12, 24).toDate();
    private static final Date CHRISTMAS_2012 = new LocalDate(2012, 12, 24).toDate();
    private static final Date NEW_YEARS_EVE_2011 = new LocalDate(2011, 12, 31).toDate();
    private static final Date NEW_YEARS_EVE_2012 = new LocalDate(2012, 12, 31).toDate();
    private static final Date BETWEEN_YEARS_START = new LocalDate(2011, 12, 19).toDate();
    private static final Date BETWEEN_YEARS_END = new LocalDate(2012, 1, 8).toDate();
    private static final Date JANUARY_1ST_2012 = new LocalDate(2012, 1, 1).toDate();
    private static final Date JANUARY_31ST_2012 = new LocalDate(2012, 1, 31).toDate();

    @Before
    public void setUp() throws Exception {
        System.setProperty("de.jollyday.config", JOLLYDAY_PROPERTIES_FILEPATH);
    }

    @Test
    public void testOneDay() throws Exception {
        Period period = new Period(FRIDAY, FRIDAY);
        assertEquals(1.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testTwoDays() throws Exception {
        Period period = new Period(THURSDAY, FRIDAY);
        assertEquals(2.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testFridayToMonday() throws Exception {
        Period period = new Period(FRIDAY, MONDAY);
        assertEquals(2.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testMonthWith21WorkDays() throws Exception {
        Period period = new Period(JANUARY_1ST_2012, JANUARY_31ST_2012);
        assertEquals(21.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testHoliday() throws Exception {
        Period period = new Period(MONDAY_BOXING_DAY, MONDAY_BOXING_DAY);
        assertEquals(0.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testHalfDayOnWeekend() throws Exception {
        Period period = new Period(CHRISTMAS_2011, CHRISTMAS_2011);
        assertEquals(0.0, holidayCalculator.calculateWorkingDays(period));
        period = new Period(NEW_YEARS_EVE_2011, NEW_YEARS_EVE_2011);
        assertEquals(0.0, holidayCalculator.calculateWorkingDays(period));
    }

    @Test
    public void testHalfDay() throws Exception {
        Period period = new Period(CHRISTMAS_2012, CHRISTMAS_2012);
        assertEquals(0.5, holidayCalculator.calculateWorkingDays(period));
        period = new Period(NEW_YEARS_EVE_2012, NEW_YEARS_EVE_2012);
        assertEquals(0.5, holidayCalculator.calculateWorkingDays(period));
    }


    @Test
    public void testBetweenYears() throws Exception {
        Period period = new Period(BETWEEN_YEARS_START, BETWEEN_YEARS_END);
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
