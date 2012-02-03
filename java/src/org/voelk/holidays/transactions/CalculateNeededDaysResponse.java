package org.voelk.holidays.transactions;

import org.voelk.holidays.ResponseModel;

public class CalculateNeededDaysResponse implements ResponseModel {
    private double days;

    public CalculateNeededDaysResponse(double days) {
        this.days = days;
    }

    public double getDays() {
        return days;
    }
}
