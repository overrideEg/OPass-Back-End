/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "name.ar", column = @Column(name = "name_ar")),
        @AttributeOverride(name = "name.en", column = @Column(name = "name_en")),
        @AttributeOverride(name = "name.tr", column = @Column(name = "name_tr")),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class subscriptionPlan extends OEntity {
    @Embedded
    @JsonProperty(required = true)
    private translatedField name;
    private Integer durationDays;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<com.overrideeg.apps.opass.io.entities.details.planDetails> planDetails;
    private Integer fromNoOfEmployees;
    private Integer toNoOfEmployees;

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    public translatedField getName() {
        return name;
    }

    public void setName(translatedField name) {
        this.name = name;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public List<com.overrideeg.apps.opass.io.entities.details.planDetails> getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(List<com.overrideeg.apps.opass.io.entities.details.planDetails> planDetails) {
        this.planDetails = planDetails;
    }

    public Integer getFromNoOfEmployees() {
        return fromNoOfEmployees;
    }

    public void setFromNoOfEmployees(Integer fromNoOfEmployees) {
        this.fromNoOfEmployees = fromNoOfEmployees;
    }

    public Integer getToNoOfEmployees() {
        return toNoOfEmployees;
    }

    public void setToNoOfEmployees(Integer toNoOfEmployees) {
        this.toNoOfEmployees = toNoOfEmployees;
    }
}
