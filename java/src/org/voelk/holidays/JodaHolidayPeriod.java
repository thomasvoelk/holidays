package org.voelk.holidays;

import org.joda.time.LocalDate;

import java.util.Date;

public class JodaHolidayPeriod implements HolidayPeriod {
    private LocalDate startDate;
    private LocalDate endDate;

    public JodaHolidayPeriod(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public Date getStartDate() {
        return startDate.toDate();
    }

    @Override
    public Date getEndDate() {
        return endDate.toDate();
    }
}
