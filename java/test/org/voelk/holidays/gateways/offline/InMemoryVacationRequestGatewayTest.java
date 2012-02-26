package org.voelk.holidays.gateways.offline;

import org.junit.*;
import org.voelk.holidays.entities.*;
import org.voelk.holidays.gateways.*;

import static org.junit.Assert.assertNotNull;
import static org.voelk.holidays.util.DateConstants.FRIDAY;
import static org.voelk.holidays.util.DateConstants.THURSDAY;

public class InMemoryVacationRequestGatewayTest {
    private VacationRequestGateway gateway;

    @Before
    public void before() {
        this.gateway = new InMemoryVacationRequestGateway();
    }

    @Test
    public void testCreate() {
        String userId = "4711";
        VacationRequest request = new VacationRequest(userId, new Period(THURSDAY, FRIDAY));
        String id = gateway.create(request);
        assertNotNull(gateway.find(id));
    }
}
