package org.voelk.holidays;

import de.jollyday.util.*;
import org.joda.time.*;

import java.util.*;

public class GregorianDateFactory {
    public static LocalDate create(int year, int month, int day) {
        return CalendarUtil.create(year, month, day);
    }

    public static LocalDate convertToGregorianDate(final Date date) {
        return CalendarUtil.convertToGregorianDate(new LocalDate(date.getTime()));
    }
}