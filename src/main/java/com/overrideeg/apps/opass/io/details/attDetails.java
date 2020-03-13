/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.details;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.overrideeg.apps.opass.enums.attType;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class attDetails extends OEntity {
    @ManyToOne
    private employee employee;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date transactionTime;
    @Enumerated(EnumType.STRING)
    private attType attType;
    private Boolean lateEntrance;
    private Boolean earlyGo;
    private Boolean overTime;

    public com.overrideeg.apps.opass.io.entities.employee getEmployee() {
        return employee;
    }

    public void setEmployee(com.overrideeg.apps.opass.io.entities.employee employee) {
        this.employee = employee;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
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
