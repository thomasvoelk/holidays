package org.voelk.holidays.application;

import org.junit.*;
import org.voelk.holidays.util.*;

import static org.junit.Assert.assertNotNull;

public class ApplicationTest {

    private Application application;

    @Before
    public void before() {
        application = ApplicationFactory.instantiateApplication();
    }

    @Test
    public void testHolidayCalculatorNotNull() {
        assertNotNull(application.getHolidayCalculator());
    }

    @Test
    public void testUserGatewayNotNull() {
        assertNotNull(application.getUserGateway());
    }

}
