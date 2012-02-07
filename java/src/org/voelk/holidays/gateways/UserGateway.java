package org.voelk.holidays.gateways;

import org.voelk.holidays.entities.*;

public interface UserGateway {
    void add(User user);

    User findByUserId(String id);

    void store(User user);
}
