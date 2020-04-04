/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.valueObjects;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import java.util.List;


@Embeddable
public class attendanceRules {

    private Integer allowedLateMinutes;
    private Integer allowedLateLeaveMinutes;
    private Integer allowedEarlyLeaveMinutes;
    private Integer maxOverTimeHours;
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> daysOff;

    public Integer getAllowedLateLeaveMinutes() {
        return allowedLateLeaveMinutes;
    }

    public void setAllowedLateLeaveMinutes(Integer allowedLateLeaveMinutes) {
        this.allowedLateLeaveMinutes = allowedLateLeaveMinutes;
    }

    public Integer getAllowedLateMinutes() {
        return allowedLateMinutes;
    }

    public void setAllowedLateMinutes(Integer allowedLateMinutes) {
        this.allowedLateMinutes = allowedLateMinutes;
    }

    public Integer getAllowedEarlyLeaveMinutes() {
        return allowedEarlyLeaveMinutes;
    }

    public void setAllowedEarlyLeaveMinutes(Integer allowedEarlyLeaveMinutes) {
        this.allowedEarlyLeaveMinutes = allowedEarlyLeaveMinutes;
    }

    public Integer getMaxOverTimeHours() {
        return maxOverTimeHours;
    }

    public void setMaxOverTimeHours(Integer maxOverTimeHours) {
        this.maxOverTimeHours = maxOverTimeHours;
    }

    public List<Integer> getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(List<Integer> daysOff) {
        this.daysOff = daysOff;
    }
}
