package org.voelk.holidays.application;

import com.google.inject.*;
import org.voelk.holidays.gateways.*;

@Singleton
public class Application {
    private HolidayCalculator holidayCalculator;
    private UserGateway userGateway;
    private UserManager userManager;

    @Inject
    Application(HolidayCalculator holidayCalculator, UserGateway userGateway) {
        this.holidayCalculator = holidayCalculator;
        this.userGateway = userGateway;
        this.userManager = new UserManager(userGateway);
    }

    public HolidayCalculator getHolidayCalculator() {
        return holidayCalculator;
    }

    public UserGateway getUserGateway() {
        return userGateway;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
