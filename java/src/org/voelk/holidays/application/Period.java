package org.voelk.holidays.application;

import java.util.*;

public class Period {
    private Date from;
    private Date to;

    public Period(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return this.from;
    }

    public Date getTo() {
        return this.to;
    }
}
