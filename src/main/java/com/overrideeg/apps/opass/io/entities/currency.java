/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;

import javax.persistence.*;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "name.ar", column = @Column(name = "name_ar")),
        @AttributeOverride(name = "name.en", column = @Column(name = "name_en")),
        @AttributeOverride(name = "fractionName.ar", column = @Column(name = "fractionName_ar")),
        @AttributeOverride(name = "fractionName.en", column = @Column(name = "fractionName_en")),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class currency extends OEntity {
    @Embedded
    @JsonProperty(required = true)
    private translatedField name;
    @Embedded
    @JsonProperty(required = true)
    private translatedField fractionName;
    private String icon;
    private Boolean defaultCurrency;

    public translatedField getName() {
        return name;
    }

    public void setName(translatedField name) {
        this.name = name;
    }

    public translatedField getFractionName() {
        return fractionName;
    }

    public void setFractionName(translatedField fractionName) {
        this.fractionName = fractionName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Boolean defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }
}
