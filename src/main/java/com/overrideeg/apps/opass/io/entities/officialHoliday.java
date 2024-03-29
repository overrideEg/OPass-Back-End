/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;

import javax.persistence.*;
import java.util.Date;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "name.ar", column = @Column(name = "name_ar")),
        @AttributeOverride(name = "name.en", column = @Column(name = "name_en")),
        @AttributeOverride(name = "name.tr", column = @Column(name = "name_tr")),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class officialHoliday extends OEntity {
    @Embedded
    private translatedField name;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date fromDate;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date toDate;

    public translatedField getName () {
        return name;
    }

    public void setName ( translatedField name ) {
        this.name = name;
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
