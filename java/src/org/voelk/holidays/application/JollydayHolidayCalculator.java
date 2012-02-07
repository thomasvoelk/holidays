package org.voelk.holidays.application;

import de.jollyday.*;
import de.jollyday.util.*;
import org.joda.time.*;

public class JollydayHolidayCalculator implements HolidayCalculator {

    private static final String BAVARIA = "by";

    @Override
    public double calculateWorkingDays(Period period) {
        LocalDate gregorianStart = CalendarUtil.convertToGregorianDate(new LocalDate(period.getFrom()));
        LocalDate gregorianEnd = CalendarUtil.convertToGregorianDate(new LocalDate(period.getTo()));
        double nonWorkDays = numberOfNonWorkdays(gregorianStart, gregorianEnd);
        return Days.daysBetween(gregorianStart, gregorianEnd).getDays() + 1 - nonWorkDays;
    }

    private double numberOfNonWorkdays(LocalDate start, LocalDate end) {
        LocalDate date = new LocalDate(start);
        double nonWorkdays = 0;
        while (date.isBefore(end) || date.isEqual(end)) {
            if (CalendarUtil.isWeekend(date)) {
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

    private boolean isHoliday(LocalDate test) {
        HolidayManager m = HolidayManager.getInstance(HolidayCalendar.GERMANY);
        return m.isHoliday(test, BAVARIA);
    }
}
