package org.voelk.holidays;

import org.joda.time.*;
import org.junit.*;
import static junit.framework.Assert.assertEquals;

public class HolidayCalculatorTest {

    private HolidayCalculator calculator;
    private static final LocalDate THURSDAY = new LocalDate(2011, 9, 8);
    private static final LocalDate FRIDAY = new LocalDate(2011, 9, 9);
    private static final LocalDate MONDAY = new LocalDate(2011, 9, 12);
    private static final LocalDate MONDAY_BOXING_DAY = new LocalDate(2011, 12, 26);
    private static final LocalDate CHRISTMAS_2011 = new LocalDate(2011, 12, 24);
    private static final LocalDate CHRISTMAS_2012 = new LocalDate(2012, 12, 24);
    private static final LocalDate BETWEEN_YEARS_START = new LocalDate(2011, 12, 19);
    private static final LocalDate BETWEEN_YEARS_END = new LocalDate(2012, 1, 8);
    private static final LocalDate JANUARY_1ST_2012 = new LocalDate(2012, 1, 1);
    private static final LocalDate JANUARY_31ST_2012 = new LocalDate(2012, 1, 31);

    @Before
    public void setUp() throws Exception {
        calculator = new HolidayCalculator();
        System.setProperty("de.jollyday.config", "singlethread_jollyday.properties");
    }

    @Test
    public void testOneDay() throws Exception {
        HolidayPeriod period = new JodaHolidayPeriod(FRIDAY, FRIDAY);
        assertEquals(1.0, calculator.daysNeededFor(period));
    }

    @Test
    public void testTwoDays() throws Exception {
        HolidayPeriod period = new JodaHolidayPeriod(THURSDAY, FRIDAY);
        assertEquals(2.0, calculator.daysNeededFor(period));
    }

    @Test
    public void testFridayToMonday() throws Exception {
        HolidayPeriod period = new JodaHolidayPeriod(FRIDAY, MONDAY);
        assertEquals(2.0, calculator.daysNeededFor(period));
    }
    
    @Test
    public void testMonthWith21WorkDays() throws Exception {
        HolidayPeriod period = new JodaHolidayPeriod(JANUARY_1ST_2012, JANUARY_31ST_2012);
        assertEquals(21.0, calculator.daysNeededFor(period));
    }

    @Test
    public void testHoliday() throws Exception {
        HolidayPeriod period = new JodaHolidayPeriod(MONDAY_BOXING_DAY, MONDAY_BOXING_DAY);
        assertEquals(0.0, calculator.daysNeededFor(period));
    }

    @Test
    public void testHalfDayOnWeekend() throws Exception {
        HolidayPeriod period = new JodaHolidayPeriod(CHRISTMAS_2011, CHRISTMAS_2011);
        assertEquals(0.0, calculator.daysNeededFor(period));
    }

    @Test
    public void testHalfDay() throws Exception {
        HolidayPeriod period = new JodaHolidayPeriod(CHRISTMAS_2012, CHRISTMAS_2012);
        assertEquals(0.5, calculator.daysNeededFor(period));
    }


    @Test
    public void testBetweenYears() throws Exception {
        HolidayPeriod period = new JodaHolidayPeriod(BETWEEN_YEARS_START, BETWEEN_YEARS_END);
        assertEquals(13.0, calculator.daysNeededFor(period));
    }
}