package org.voelk.holidays.transactions;

import org.joda.time.*;
import org.junit.*;
import org.voelk.holidays.Transaction;

import static junit.framework.Assert.assertEquals;

public class CalculateNeededDaysTransactionTest {

    public static final String JOLLYDAY_PROPERTIES_FILEPATH = "d:/development/projekte/holidays/java/test/singlethread_jollyday.properties";
    private Transaction<CalculateNeededDaysRequest, CalculateNeededDaysResponse> transaction;
    private static final LocalDate THURSDAY = new LocalDate(2011, 9, 8);
    private static final LocalDate FRIDAY = new LocalDate(2011, 9, 9);
    private static final LocalDate MONDAY = new LocalDate(2011, 9, 12);
    private static final LocalDate MONDAY_BOXING_DAY = new LocalDate(2011, 12, 26);
    private static final LocalDate CHRISTMAS_2011 = new LocalDate(2011, 12, 24);
    private static final LocalDate CHRISTMAS_2012 = new LocalDate(2012, 12, 24);
    private static final LocalDate NEW_YEARS_EVE_2011 = new LocalDate(2011, 12, 31);
    private static final LocalDate NEW_YEARS_EVE_2012 = new LocalDate(2012, 12, 31);
    private static final LocalDate BETWEEN_YEARS_START = new LocalDate(2011, 12, 19);
    private static final LocalDate BETWEEN_YEARS_END = new LocalDate(2012, 1, 8);
    private static final LocalDate JANUARY_1ST_2012 = new LocalDate(2012, 1, 1);
    private static final LocalDate JANUARY_31ST_2012 = new LocalDate(2012, 1, 31);

    @Before
    public void setUp() throws Exception {
        transaction = new CalculateNeededDaysTransaction();
        System.setProperty("de.jollyday.config", JOLLYDAY_PROPERTIES_FILEPATH);
    }

    @Test
    public void testOneDay() throws Exception {
        CalculateNeededDaysRequest request = new JodaCalculateNeededDaysRequest(FRIDAY, FRIDAY);
        assertEquals(1.0, transaction.execute(request).getDays());
    }

    @Test
    public void testTwoDays() throws Exception {
        CalculateNeededDaysRequest request = new JodaCalculateNeededDaysRequest(THURSDAY, FRIDAY);
        assertEquals(2.0, transaction.execute(request).getDays());
    }

    @Test
    public void testFridayToMonday() throws Exception {
        CalculateNeededDaysRequest request = new JodaCalculateNeededDaysRequest(FRIDAY, MONDAY);
        assertEquals(2.0, transaction.execute(request).getDays());
    }
    
    @Test
    public void testMonthWith21WorkDays() throws Exception {
        CalculateNeededDaysRequest request = new JodaCalculateNeededDaysRequest(JANUARY_1ST_2012, JANUARY_31ST_2012);
        assertEquals(21.0, transaction.execute(request).getDays());
    }

    @Test
    public void testHoliday() throws Exception {
        CalculateNeededDaysRequest request = new JodaCalculateNeededDaysRequest(MONDAY_BOXING_DAY, MONDAY_BOXING_DAY);
        assertEquals(0.0, transaction.execute(request).getDays());
    }

    @Test
    public void testHalfDayOnWeekend() throws Exception {
        CalculateNeededDaysRequest request = new JodaCalculateNeededDaysRequest(CHRISTMAS_2011, CHRISTMAS_2011);
        assertEquals(0.0, transaction.execute(request).getDays());
        request = new JodaCalculateNeededDaysRequest(NEW_YEARS_EVE_2011, NEW_YEARS_EVE_2011);
        assertEquals(0.0, transaction.execute(request).getDays());
    }

    @Test
    public void testHalfDay() throws Exception {
        CalculateNeededDaysRequest request = new JodaCalculateNeededDaysRequest(CHRISTMAS_2012, CHRISTMAS_2012);
        assertEquals(0.5, transaction.execute(request).getDays());
        request = new JodaCalculateNeededDaysRequest(NEW_YEARS_EVE_2012, NEW_YEARS_EVE_2012);
        assertEquals(0.5, transaction.execute(request).getDays());
    }


    @Test
    public void testBetweenYears() throws Exception {
        CalculateNeededDaysRequest request = new JodaCalculateNeededDaysRequest(BETWEEN_YEARS_START, BETWEEN_YEARS_END);
        assertEquals(13.0, transaction.execute(request).getDays());
    }
}