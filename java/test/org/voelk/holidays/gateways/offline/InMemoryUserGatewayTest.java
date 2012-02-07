package org.voelk.holidays.gateways.offline;

import org.junit.*;
import org.voelk.holidays.entities.*;
import org.voelk.holidays.gateways.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InMemoryUserGatewayTest {
    private UserGateway gateway;

    @Before
    public void before() {
        this.gateway = new InMemoryUserGateway();
    }

    @Test
    public void testFindById() {
        User user = new User("4711");
        gateway.add(user);
        user = gateway.findByUserId("4711");
        assertEquals(user.getUserId(), "4711");
        try {
            user = gateway.findByUserId("4712");
            fail();
        } catch (ObjectNotFoundException ex) {

        }
    }

}
