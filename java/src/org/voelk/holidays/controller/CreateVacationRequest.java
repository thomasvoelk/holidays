package org.voelk.holidays.controller;

import org.voelk.holidays.application.*;
import org.voelk.holidays.entities.*;

public class CreateVacationRequest {
    public String execute(VacationRequest request) {
        return Application.getVacationRequestGateway().create(request);
    }
}
