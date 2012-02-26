package org.voelk.holidays.application;

import com.google.inject.*;
import org.voelk.holidays.gateways.*;

@Singleton
public class Application {
    private HolidayCalculator holidayCalculator;
    private UserGateway userGateway;
    private UserManager userManager;
    private static VacationRequestGateway vacationRequestGateway;

    @Inject
    Application(UserGateway userGateway, VacationRequestGateway vacationRequestGateway) {
        this.userGateway = userGateway;
        Application.vacationRequestGateway = vacationRequestGateway;
        this.holidayCalculator = new JollydayHolidayCalculator();
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

    public static VacationRequestGateway getVacationRequestGateway() {
        return vacationRequestGateway;
    }
}
