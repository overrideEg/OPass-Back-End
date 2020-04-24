/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities.HR;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedLob;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hr_permissions")
@AttributeOverrides({
        @AttributeOverride(name = "description.ar", column = @Column(name = "description_ar")),
        @AttributeOverride(name = "description.en", column = @Column(name = "description_en")),
        @AttributeOverride(name = "description.tr", column = @Column(name = "description_tr")),
})
public class HRPermissions extends OEntity {
    @Embedded
    private translatedLob description;
    @ManyToOne(fetch = FetchType.EAGER)
    private employee employee;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date date;
    private Integer minutesLate;
    private Integer minutesEarlyGo;
    private Boolean absenceAllowed;

    public translatedLob getDescription () {
        return description;
    }

    public void setDescription ( translatedLob description ) {
        this.description = description;
    }

    public com.overrideeg.apps.opass.io.entities.employee getEmployee () {
        return employee;
    }

    public void setEmployee ( com.overrideeg.apps.opass.io.entities.employee employee ) {
        this.employee = employee;
    }

    public Date getDate () {
        return date;
    }

    public void setDate ( Date date ) {
        this.date = date;
    }

    public Integer getMinutesLate () {
        return minutesLate;
    }

    public void setMinutesLate ( Integer hoursLate ) {
        this.minutesLate = hoursLate;
    }

    public Integer getMinutesEarlyGo () {
        return minutesEarlyGo;
    }

    public void setMinutesEarlyGo ( Integer hoursEarlyGo ) {
        this.minutesEarlyGo = hoursEarlyGo;
    }

    public Boolean getAbsenceAllowed () {
        return absenceAllowed;
    }

    public void setAbsenceAllowed ( Boolean absenceAllowed ) {
        this.absenceAllowed = absenceAllowed;
    }
}
