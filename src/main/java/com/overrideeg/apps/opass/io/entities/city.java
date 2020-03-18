/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;

import javax.persistence.*;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "name.ar", column = @Column(name = "name_ar")),
        @AttributeOverride(name = "name.en", column = @Column(name = "name_en"))
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class city extends OEntity {
    @Embedded
    @JsonProperty(required = true)
    private translatedField name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private country country;

    public translatedField getName() {
        return name;
    }

    public void setName(translatedField name) {
        this.name = name;
    }

    public com.overrideeg.apps.opass.io.entities.country getCountry() {
        return country;
    }

    public void setCountry(com.overrideeg.apps.opass.io.entities.country country) {
        this.country = country;
    }
}
