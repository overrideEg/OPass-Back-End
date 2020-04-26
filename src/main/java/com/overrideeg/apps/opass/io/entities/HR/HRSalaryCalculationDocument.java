/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities.HR;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.overrideeg.apps.opass.io.entities.branch;
import com.overrideeg.apps.opass.io.entities.department;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedLob;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "salary_calculation")
@AttributeOverrides({
        @AttributeOverride(name = "description.ar", column = @Column(name = "description_ar")),
        @AttributeOverride(name = "description.en", column = @Column(name = "description_en")),
        @AttributeOverride(name = "description.tr", column = @Column(name = "description_tr")),
})
public class HRSalaryCalculationDocument extends OEntity {
    @Embedded
    private translatedLob description;
    @ManyToOne(fetch = FetchType.EAGER)
    private department department;
    @ManyToOne(fetch = FetchType.EAGER)
    private branch branch;
    @ManyToOne(fetch = FetchType.EAGER)
    private employee employee;
    @JsonFormat(timezone = "Africa/Cairo")
    private Date fromDate;
    @JsonFormat(timezone = "Africa/Cairo")
    private Date toDate;

    public translatedLob getDescription () {
        return description;
    }

    public void setDescription ( translatedLob description ) {
        this.description = description;
    }

    public com.overrideeg.apps.opass.io.entities.department getDepartment () {
        return department;
    }

    public void setDepartment ( com.overrideeg.apps.opass.io.entities.department department ) {
        this.department = department;
    }

    public com.overrideeg.apps.opass.io.entities.branch getBranch () {
        return branch;
    }

    public void setBranch ( com.overrideeg.apps.opass.io.entities.branch branch ) {
        this.branch = branch;
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
}
