package org.voelk.holidays.gateways.offline;

import org.voelk.holidays.entities.*;
import org.voelk.holidays.gateways.*;

import java.util.*;

public class InMemoryVacationRequestGateway extends InMemoryGateway<VacationRequest> implements VacationRequestGateway {

    private Map<String, VacationRequest> requests = new LinkedHashMap<String, VacationRequest>();

    @Override
    public String create(VacationRequest vacationRequest) {
        String id = String.valueOf(requests.size());
        requests.put(id, vacationRequest);
        return id;
    }

    @Override
    public VacationRequest find(String id) {
        return requests.get(id);
    }

    @Override
    protected String getId(VacationRequest entity) {
        if (entity.getId() == null) {
            return UUID.randomUUID().toString();
        } else {
            return entity.getId();
        }
    }
}
