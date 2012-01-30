package org.voelk.holidays;

import de.jollyday.util.CalendarUtil;
import org.joda.time.LocalDate;

public class GregorianDateFactory {
    public static LocalDate create(int year, int month, int day) {
        return CalendarUtil.create(year, month, day);
    }

    public static LocalDate convertToGregorianDate(final LocalDate date) {
        return CalendarUtil.convertToGregorianDate(date);
    }
}