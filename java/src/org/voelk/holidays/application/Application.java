package org.voelk.holidays.application;

public class Application {
    private HolidayCalculator holidayCalculator;

    public Application() {
        holidayCalculator = new HolidayCalculator();
    }

    public HolidayCalculator getHolidayCalculator() {
        return holidayCalculator;
    }
}
