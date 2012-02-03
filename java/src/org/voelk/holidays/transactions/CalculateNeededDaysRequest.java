package org.voelk.holidays.transactions;

import org.voelk.holidays.RequestModel;

import java.util.Date;

public interface CalculateNeededDaysRequest extends RequestModel {
    Date getStartDate();

    Date getEndDate();
}
