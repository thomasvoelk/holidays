package org.voelk.holidays.gateways.offline;

import org.voelk.holidays.entities.*;
import org.voelk.holidays.gateways.*;

import java.util.*;

public class InMemoryUserGateway implements UserGateway {

    private Map<String, User> users = new LinkedHashMap<String, User>();

    @Override
    public void add(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public User findByUserId(String id) {
        User user = users.get(id);
        if (user == null) {
            throw new ObjectNotFoundException(User.class.getCanonicalName(), id);
        }
        return user;
    }

    @Override
    public void store(User user) {
        users.put(user.getUserId(), user);
    }
}
