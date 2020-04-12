/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.overrideeg.apps.opass.enums.attStatus;
import com.overrideeg.apps.opass.enums.attType;
import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class attendance extends OEntity {
    @ManyToOne
    private employee employee;
    @ManyToOne
    private workShift workShift;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, lenient = OptBoolean.TRUE)
    private Date scanDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "hh:mm:ss", lenient = OptBoolean.TRUE, shape = JsonFormat.Shape.STRING)
    private Date scanTime;
    @Enumerated(EnumType.STRING)
    private attType attType;
    @Enumerated(EnumType.STRING)
    private attStatus attStatus;

    public attendance() {
    }

    public attendance(com.overrideeg.apps.opass.io.entities.employee employee, com.overrideeg.apps.opass.io.entities.workShift workShift, Date scanDate, Date scanTime, com.overrideeg.apps.opass.enums.attType attType, com.overrideeg.apps.opass.enums.attStatus attStatus) {
        this.employee = employee;
        this.workShift = workShift;
        this.scanDate = scanDate;
        this.scanTime = scanTime;
        this.attType = attType;
        this.attStatus = attStatus;
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    public com.overrideeg.apps.opass.io.entities.employee getEmployee() {
        return employee;
    }

    public void setEmployee(com.overrideeg.apps.opass.io.entities.employee employee) {
        this.employee = employee;
    }

    public com.overrideeg.apps.opass.io.entities.workShift getWorkShift() {
        return workShift;
    }

    public void setWorkShift(com.overrideeg.apps.opass.io.entities.workShift workShift) {
        this.workShift = workShift;
    }

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    public Date getScanTime() {
        return scanTime;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }

    public com.overrideeg.apps.opass.enums.attType getAttType() {
        return attType;
    }

    public void setAttType(com.overrideeg.apps.opass.enums.attType attType) {
        this.attType = attType;
    }

    public com.overrideeg.apps.opass.enums.attStatus getAttStatus() {
        return attStatus;
    }

    public void setAttStatus(com.overrideeg.apps.opass.enums.attStatus attStatus) {
        this.attStatus = attStatus;
    }
}
