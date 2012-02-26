package org.voelk.holidays.entities;

public class VacationRequest {
    private String id;
    private String userId;
    private Period period;

    public VacationRequest(String userId, Period period) {
        this.userId = userId;
        this.period = period;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
