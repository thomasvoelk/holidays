package org.voelk.holidays.gateways.offline;

import org.voelk.holidays.entities.*;
import org.voelk.holidays.gateways.*;

public class InMemoryUserGateway extends InMemoryGateway<User> implements UserGateway {

    @Override
    public void add(User user) {
        entities.put(user.getUserId(), user);
    }

    @Override
    public User findByUserId(String id) {
        User user = entities.get(id);
        if (user == null) {
            throw new ObjectNotFoundException(User.class.getCanonicalName(), id);
        }
        return user;
    }


    @Override
    protected String getId(User entity) {
        return entity.getUserId();
    }
}
