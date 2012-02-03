package org.voelk.holidays.transactions;

import de.jollyday.*;
import org.joda.time.*;
import org.voelk.holidays.GregorianDateFactory;
import org.voelk.holidays.Transaction;

public class CalculateNeededDaysTransaction implements Transaction<CalculateNeededDaysRequest, CalculateNeededDaysResponse> {

    private static final String BAVARIA = "by";

    public CalculateNeededDaysResponse execute(CalculateNeededDaysRequest period) {
        return new CalculateNeededDaysResponse(daysNeededFor(new LocalDate(period.getStartDate()), new LocalDate(period.getEndDate())));
    }

    private double daysNeededFor(final LocalDate start, final LocalDate end) {
        LocalDate gregorianStart = GregorianDateFactory.convertToGregorianDate(start);
        LocalDate gregorianEnd = GregorianDateFactory.convertToGregorianDate(end);
        double nonWorkDays = numberOfNonWorkdays(gregorianStart, gregorianEnd);
        return Days.daysBetween(gregorianStart, gregorianEnd).getDays() + 1 - nonWorkDays;
    }

    private double numberOfNonWorkdays(LocalDate start, LocalDate end) {
        LocalDate date = new LocalDate(start);
        double nonWorkdays = 0;
        while (date.isBefore(end) || date.isEqual(end)) {
            if (isWeekend(date)) {
                nonWorkdays++;
            } else if (isHalfDayHoliday(date)) {
                nonWorkdays += 0.5;
            } else if (isHoliday(date))
                nonWorkdays++;
            date = date.plusDays(1);
        }
        return nonWorkdays;
    }

    private boolean isHalfDayHoliday(LocalDate date) {
        return isChristmas(date) || isNewYearsEve(date);
    }

    private boolean isChristmas(LocalDate date) {
        return date.getMonthOfYear() == 12 && date.getDayOfMonth() == 24;
    }

    private boolean isNewYearsEve(LocalDate date) {
        return date.getMonthOfYear() == 12 && date.getDayOfMonth() == 31;
    }

    private boolean isWeekend(LocalDate test) {
        return test.getDayOfWeek() == DateTimeConstants.SATURDAY || test.getDayOfWeek() == DateTimeConstants.SUNDAY;
    }

    private boolean isHoliday(LocalDate test) {
        HolidayManager m = HolidayManager.getInstance(HolidayCalendar.GERMANY);
        return m.isHoliday(test, BAVARIA);
    }
}