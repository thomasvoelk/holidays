package org.voelk.holidays.application;

import org.junit.*;

import static org.junit.Assert.assertNotNull;

public class ApplicationTest {

    private Application application;

    @Before
    public void before() {
        application = new Application();
    }

    @Test
    public void testHolidayCalculatorNotNull() {
        assertNotNull(application.getHolidayCalculator());
    }
}
