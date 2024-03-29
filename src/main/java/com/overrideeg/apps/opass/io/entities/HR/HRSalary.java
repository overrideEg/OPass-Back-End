/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities.HR;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "salary")
public class HRSalary extends OEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private employee employee;
    @Temporal(TemporalType.DATE)
    @JsonFormat()
    private Date fromDate;
    @Temporal(TemporalType.DATE)
    @JsonFormat()
    private Date toDate;
    private Integer totalHours;
    private Integer overTimeHours;
    private Integer lateMinutes;
    private Integer absenceDays;
    private Integer earlyGoMinutes;
    private Double salary;
    private Double addition;
    private Double deduction;
    private Double total;

    @PrePersist
    public void PrePersist () {
        if (addition == null)
            addition = 0D;
        if (deduction == null)
            deduction = 0D;
        total = salary + addition - deduction;
    }

    @PreUpdate
    public void PreUpdate () {
        if (addition == null)
            addition = 0D;
        if (deduction == null)
            deduction = 0D;
        total = salary + addition - deduction;
    }


    public com.overrideeg.apps.opass.io.entities.employee getEmployee () {
        return employee;
    }

    public void setEmployee ( com.overrideeg.apps.opass.io.entities.employee employee ) {
        this.employee = employee;
    }

    public Date getFromDate () {
        return fromDate;
    }

    public void setFromDate ( Date fromDate ) {
        this.fromDate = fromDate;
    }

    public Date getToDate () {
        return toDate;
    }

    public void setToDate ( Date toDate ) {
        this.toDate = toDate;
    }

    public Double getSalary () {
        return salary;
    }

    public void setSalary ( Double salary ) {
        this.salary = salary;
    }

    public Double getAddition () {
        return addition;
    }

    public void setAddition ( Double addition ) {
        this.addition = addition;
    }

    public Double getDeduction () {
        return deduction;
    }

    public void setDeduction ( Double deduction ) {
        this.deduction = deduction;
    }

    public Double getTotal () {
        return total;
    }

    public void setTotal ( Double total ) {
        this.total = total;
    }


    public Integer getTotalHours () {
        return totalHours;
    }

    public void setTotalHours ( Integer totalHours ) {
        this.totalHours = totalHours;
    }

    public Integer getOverTimeHours () {
        return overTimeHours;
    }

    public void setOverTimeHours ( Integer overTimeHours ) {
        this.overTimeHours = overTimeHours;
    }

    public Integer getLateMinutes () {
        return lateMinutes;
    }

    public void setLateMinutes ( Integer lateMinutes ) {
        this.lateMinutes = lateMinutes;
    }

    public Integer getAbsenceDays () {
        return absenceDays;
    }

    public void setAbsenceDays ( Integer absenceDays ) {
        this.absenceDays = absenceDays;
    }

    public Integer getEarlyGoMinutes () {
        return earlyGoMinutes;
    }

    public void setEarlyGoMinutes ( Integer earlyGoMinutes ) {
        this.earlyGoMinutes = earlyGoMinutes;
    }

}
