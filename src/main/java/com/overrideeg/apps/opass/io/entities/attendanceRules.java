/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class attendanceRules extends OEntity {

    private Integer allowedLateMinutes;
    private Integer allowedEarlyLeaveMinutes;
    private Integer maxOverTimeHours;
    @ElementCollection
    private List<Integer> daysOff;


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
