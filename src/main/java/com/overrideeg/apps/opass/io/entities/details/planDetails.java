/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities.details;

import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class planDetails extends OEntity {
    @ManyToOne
    private com.overrideeg.apps.opass.io.entities.currency currency;
    private Double price;

    public com.overrideeg.apps.opass.io.entities.currency getCurrency() {
        return currency;
    }

    public void setCurrency(com.overrideeg.apps.opass.io.entities.currency currency) {
        this.currency = currency;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
