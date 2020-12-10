/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities.HR;

import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "hr_settins")
public class HRSettings extends OEntity {
    private Double absenceDayDeduction;
    private Double delayHourDeduction;
    private Double overTimeAddition;
    private Double workOnDayOffAddition;
    @Column(columnDefinition="tinyint(1) default 1")
    private Boolean checkDepartment;
    @Column(columnDefinition="tinyint(1) default 1")
    private Boolean checkFingerprint;
    @Column(columnDefinition="tinyint(1) default 1")
    private Boolean checkFaceRecognition;
    @Column(columnDefinition="tinyint(1) default 1")
    private Boolean checkLocation;


    public Double getAbsenceDayDeduction() {
        return absenceDayDeduction;
    }

    public void setAbsenceDayDeduction(Double absenceDayDeduction) {
        this.absenceDayDeduction = absenceDayDeduction;
    }

    public Double getDelayHourDeduction() {
        return delayHourDeduction;
    }

    public void setDelayHourDeduction(Double delayHourDeduction) {
        this.delayHourDeduction = delayHourDeduction;
    }

    public Double getOverTimeAddition() {
        return overTimeAddition;
    }

    public void setOverTimeAddition(Double overTimeAddition) {
        this.overTimeAddition = overTimeAddition;
    }

    public Double getWorkOnDayOffAddition() {
        return workOnDayOffAddition;
    }

    public void setWorkOnDayOffAddition(Double workOnDayOffAddition) {
        this.workOnDayOffAddition = workOnDayOffAddition;
    }

    public Boolean getCheckDepartment() {
        return checkDepartment;
    }

    public void setCheckDepartment(Boolean checkDepartment) {
        this.checkDepartment = checkDepartment;
    }

    public Boolean getCheckFingerprint() {
        return checkFingerprint;
    }

    public void setCheckFingerprint(Boolean checkFingerprint) {
        this.checkFingerprint = checkFingerprint;
    }

    public Boolean getCheckFaceRecognition() {
        return checkFaceRecognition;
    }

    public void setCheckFaceRecognition(Boolean checkFaceRecognition) {
        this.checkFaceRecognition = checkFaceRecognition;
    }

    public Boolean getCheckLocation() {
        return checkLocation;
    }

    public void setCheckLocation(Boolean checkLocation) {
        this.checkLocation = checkLocation;
    }
}
