package org.voelk.holidays.transactions;

import org.joda.time.*;
import java.util.*;

public class JodaCalculateNeededDaysRequest implements CalculateNeededDaysRequest {
    private LocalDate startDate;
    private LocalDate endDate;

    public JodaCalculateNeededDaysRequest(LocalDate startDate, LocalDate endDate) {
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
