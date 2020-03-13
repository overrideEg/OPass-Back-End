/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.overrideeg.apps.opass.io.details.attDetails;
import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.*;
import java.time.Month;
import java.util.Date;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class attendance extends OEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date valueDate;
    private Month month;
    @ManyToOne
    private company company;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<attDetails> details;

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public com.overrideeg.apps.opass.io.entities.company getCompany() {
        return company;
    }

    public void setCompany(com.overrideeg.apps.opass.io.entities.company company) {
        this.company = company;
    }

    public List<attDetails> getDetails() {
        return details;
    }

    public void setDetails(List<attDetails> details) {
        this.details = details;
    }
}
