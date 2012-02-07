package org.voelk.holidays.application;

import org.voelk.holidays.entities.*;
import org.voelk.holidays.gateways.*;

public class UserManager {

    private UserGateway userGateway;

    public UserManager(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void setHolidaysForUser(String userId, int holidays) {
        User user = userGateway.findByUserId(userId);
        user.setHolidays(holidays);
        userGateway.store(user);
    }
}
