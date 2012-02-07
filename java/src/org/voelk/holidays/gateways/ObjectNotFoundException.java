package org.voelk.holidays.gateways;

public class ObjectNotFoundException extends RuntimeException {

    private String kind;
    private String id;

    public ObjectNotFoundException(String kind, String id) {
        super();
        this.kind = kind;
        this.id = id;
    }

}
