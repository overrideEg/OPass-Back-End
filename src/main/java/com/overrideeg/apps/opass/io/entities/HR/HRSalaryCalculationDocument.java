/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities.HR;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "salary_calculation")
public class HRSalaryCalculationDocument extends OEntity {
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Africa/Cairo")
    private Date fromDate;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Africa/Cairo")
    private Date toDate;

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
}
