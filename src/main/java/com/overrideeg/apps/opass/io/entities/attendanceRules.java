/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.DayOfWeek;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class attendanceRules extends OEntity {
    @OneToOne
    private company company;
    private Integer allowedLateMinutes;
    private Integer allowedEarlyMinutes;
    private Integer maxOverTimeHours;
    @ElementCollection
    private List<DayOfWeek> daysOff;

    public com.overrideeg.apps.opass.io.entities.company getCompany() {
        return company;
    }

    public void setCompany(com.overrideeg.apps.opass.io.entities.company company) {
        this.company = company;
    }

    public Integer getAllowedLateMinutes() {
        return allowedLateMinutes;
    }

    public void setAllowedLateMinutes(Integer allowedLateMinutes) {
        this.allowedLateMinutes = allowedLateMinutes;
    }

    public Integer getAllowedEarlyMinutes() {
        return allowedEarlyMinutes;
    }

    public void setAllowedEarlyMinutes(Integer allowedEarlyMinutes) {
        this.allowedEarlyMinutes = allowedEarlyMinutes;
    }

    public Integer getMaxOverTimeHours() {
        return maxOverTimeHours;
    }

    public void setMaxOverTimeHours(Integer maxOverTimeHours) {
        this.maxOverTimeHours = maxOverTimeHours;
    }

    public List<DayOfWeek> getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(List<DayOfWeek> daysOff) {
        this.daysOff = daysOff;
    }
}
