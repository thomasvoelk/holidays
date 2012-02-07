package org.voelk.holidays.application;

import org.junit.*;
import org.voelk.holidays.entities.*;
import org.voelk.holidays.util.*;

import static org.junit.Assert.assertEquals;

public class UserManagerTest {
    private Application application;

    @Before
    public void before() {
        application = ApplicationFactory.instantiateApplication();
    }

    @Test
    public void testSetHolidaysForUser() {
        User user = new User("4711");
        application.getUserGateway().add(user);
        application.getUserManager().setHolidaysForUser("4711", 42);
        user = application.getUserGateway().findByUserId("4711");
        assertEquals(42, user.getHolidays());
    }
}
