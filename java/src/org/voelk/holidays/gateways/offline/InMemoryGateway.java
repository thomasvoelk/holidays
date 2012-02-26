package org.voelk.holidays.gateways.offline;

import java.util.*;

public abstract class InMemoryGateway<T> {
    protected Map<String, T> entities = new LinkedHashMap<String, T>();

    public void store(T entity) {
        entities.put(getId(entity), entity);
    }

    protected abstract String getId(T entity);

}
