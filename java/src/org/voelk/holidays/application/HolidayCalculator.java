package org.voelk.holidays.application;

import org.voelk.holidays.entities.*;

import java.util.*;

public interface HolidayCalculator {
    double calculateWorkingDays(Period period);

    Set<Holiday> getHolidaysFor(int year);

    Set<Holiday> getHalfDayHolidaysFor(int year);
}
