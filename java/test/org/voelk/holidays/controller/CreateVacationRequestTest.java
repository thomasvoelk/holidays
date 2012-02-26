package org.voelk.holidays.controller;

import org.joda.time.*;
import org.junit.*;
import org.voelk.holidays.application.*;
import org.voelk.holidays.entities.Period;
import org.voelk.holidays.entities.*;
import org.voelk.holidays.util.*;

import java.util.*;

import static org.junit.Assert.assertNotNull;

public class CreateVacationRequestTest {
    private static final Date THURSDAY = new LocalDate(2011, 9, 8).toDate();
    private static final Date FRIDAY = new LocalDate(2011, 9, 9).toDate();

    @Test
    public void testExecute() {
        Application application = ApplicationFactory.instantiateApplication();
        CreateVacationRequest controller = new CreateVacationRequest();
        String userId = "4711";
        Period period = new Period(THURSDAY, FRIDAY);
        VacationRequest request = new VacationRequest(userId, period);
        String id = controller.execute(request);
        assertNotNull(Application.getVacationRequestGateway().find(id));
    }
}
