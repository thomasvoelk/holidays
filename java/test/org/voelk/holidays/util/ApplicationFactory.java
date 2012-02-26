package org.voelk.holidays.util;

import com.google.inject.*;
import org.voelk.holidays.application.*;
import org.voelk.holidays.gateways.*;
import org.voelk.holidays.gateways.offline.*;

public class ApplicationFactory {

    public static Application instantiateApplication() {
        Injector injector = Guice.createInjector(new ApplicationModule());
        return injector.getInstance(Application.class);
    }

    static class ApplicationModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(HolidayCalculator.class).to(JollydayHolidayCalculator.class);
            bind(UserGateway.class).to(InMemoryUserGateway.class);
            bind(VacationRequestGateway.class).to(InMemoryVacationRequestGateway.class);
        }
    }
}
