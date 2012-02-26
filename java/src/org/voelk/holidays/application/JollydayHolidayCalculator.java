package org.voelk.holidays.application;

import de.jollyday.*;
import de.jollyday.util.*;
import org.joda.time.*;

import java.util.*;

public class JollydayHolidayCalculator implements HolidayCalculator {

    private static final String BAVARIA = "by";
    private static final Set<Day> halfDayHolidaysForLa2;

    static {
        halfDayHolidaysForLa2 = new HashSet<Day>();
        halfDayHolidaysForLa2.add(new Day(24, 12));
        halfDayHolidaysForLa2.add(new Day(31, 12));
    }

    @Override
    public double calculateWorkingDays(org.voelk.holidays.entities.Period period) {
        LocalDate gregorianStart = CalendarUtil.convertToGregorianDate(new LocalDate(period.getFrom()));
        LocalDate gregorianEnd = CalendarUtil.convertToGregorianDate(new LocalDate(period.getTo()));
        double nonWorkDays = numberOfNonWorkdays(gregorianStart, gregorianEnd);
        return Days.daysBetween(gregorianStart, gregorianEnd).getDays() + 1 - nonWorkDays;
    }

    @Override
    public Set<org.voelk.holidays.entities.Holiday> getHolidaysFor(int year) {
        HolidayManager m = HolidayManager.getInstance(HolidayCalendar.GERMANY);
        return transform(m.getHolidays(2012, BAVARIA));
    }

    @Override
    public Set<org.voelk.holidays.entities.Holiday> getHalfDayHolidaysFor(int year) {
        return transform(halfDayHolidaysForLa2, year);
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
        return halfDayHolidaysForLa2.contains(new Day(date.getDayOfMonth(), date.getMonthOfYear()));
    }

    private boolean isHoliday(LocalDate test) {
        HolidayManager m = HolidayManager.getInstance(HolidayCalendar.GERMANY);
        return m.isHoliday(test, BAVARIA);
    }

    private Set<org.voelk.holidays.entities.Holiday> transform(Set<de.jollyday.Holiday> holidays) {
        Set<org.voelk.holidays.entities.Holiday> ret = new HashSet<org.voelk.holidays.entities.Holiday>();
        for (Holiday holiday : holidays) {
            ret.add(new org.voelk.holidays.entities.Holiday(holiday.getDate().toDate()));
        }
        return ret;
    }


    private Set<org.voelk.holidays.entities.Holiday> transform(Set<Day> halfDayHolidaysForLa2, int year) {
        Set<org.voelk.holidays.entities.Holiday> ret = new HashSet<org.voelk.holidays.entities.Holiday>();
        for (Day day : halfDayHolidaysForLa2) {
            ret.add(new org.voelk.holidays.entities.Holiday(new LocalDate(year, day.monthOfYear, day.dayOfMonth).toDate()));
        }
        return ret;
    }


    private static class Day {
        private int dayOfMonth;
        private int monthOfYear;

        private Day(int dayOfMonth, int month) {
            this.dayOfMonth = dayOfMonth;
            this.monthOfYear = month;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Day day = (Day) o;
            return dayOfMonth == day.dayOfMonth && monthOfYear == day.monthOfYear;
        }

        @Override
        public int hashCode() {
            int result = dayOfMonth;
            result = 31 * result + monthOfYear;
            return result;
        }
    }

}
