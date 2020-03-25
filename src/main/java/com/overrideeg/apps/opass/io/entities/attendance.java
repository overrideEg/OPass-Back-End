/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.overrideeg.apps.opass.enums.attType;
import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class attendance extends OEntity {
    @ManyToOne
    private employee employee;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, lenient = OptBoolean.TRUE, timezone = "Africa/Cairo")
    private Date scanDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "hh:mm:ss", lenient = OptBoolean.TRUE, shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date scanTime;
    @Enumerated(EnumType.STRING)
    private attType attType;
    private Boolean lateEntrance;
    private Boolean earlyGo;
    private Boolean overTime;

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

    public Boolean getLateEntrance() {
        return lateEntrance;
    }

    public void setLateEntrance(Boolean lateEntrance) {
        this.lateEntrance = lateEntrance;
    }

    public Boolean getEarlyGo() {
        return earlyGo;
    }

    public void setEarlyGo(Boolean earlyGo) {
        this.earlyGo = earlyGo;
    }

    public Boolean getOverTime() {
        return overTime;
    }

    public void setOverTime(Boolean overTime) {
        this.overTime = overTime;
    }
}
