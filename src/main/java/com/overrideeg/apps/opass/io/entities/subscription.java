/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class subscription extends OEntity {
    @ManyToOne
    private company company;
    @ManyToOne
    private subscriptionPlan subscriptionPlan;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, lenient = OptBoolean.TRUE, timezone = "Africa/Cairo")
    private Date fromDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, lenient = OptBoolean.TRUE, timezone = "Africa/Cairo")
    private Date toDate;
    private Double subscriptionPrice;
    private Integer maxNoOfEmployees;

//    @PrePersist
//    public void PrePersist() {
//        // todo add price here assuming to fromdate to date "calc dates 🙄"
//    }


    public com.overrideeg.apps.opass.io.entities.company getCompany() {
        return company;
    }

    public void setCompany(com.overrideeg.apps.opass.io.entities.company company) {
        this.company = company;
    }

    public com.overrideeg.apps.opass.io.entities.subscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(com.overrideeg.apps.opass.io.entities.subscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Double getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(Double subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public Integer getMaxNoOfEmployees() {
        return maxNoOfEmployees;
    }

    public void setMaxNoOfEmployees(Integer maxNoOfEmployees) {
        this.maxNoOfEmployees = maxNoOfEmployees;
    }
}
