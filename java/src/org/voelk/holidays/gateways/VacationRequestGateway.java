package org.voelk.holidays.gateways;

import org.voelk.holidays.entities.*;

public interface VacationRequestGateway {
    String create(VacationRequest vacationRequest);

    VacationRequest find(String id);
}
