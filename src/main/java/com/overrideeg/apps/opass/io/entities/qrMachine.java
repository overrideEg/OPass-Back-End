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
        @AttributeOverride(name = "name.tr", column = @Column(name = "name_tr")),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class qrMachine extends OEntity {
    @Embedded
    private translatedField name;
    @ManyToOne
    @JsonProperty(required = true)
    private branch branch;
    @ManyToOne
    @JsonProperty(required = true)
    private department department;
    private Boolean isStatic;
    @Column(unique = true)
    private String macAddress;
    @JsonProperty(required = true)
    private Long changeDuration;

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

    public com.overrideeg.apps.opass.io.entities.branch getBranch() {
        return branch;
    }

    public void setBranch(com.overrideeg.apps.opass.io.entities.branch branch) {
        this.branch = branch;
    }

    public com.overrideeg.apps.opass.io.entities.department getDepartment() {
        return department;
    }

    public void setDepartment(com.overrideeg.apps.opass.io.entities.department department) {
        this.department = department;
    }

    public Boolean getStatic() {
        return isStatic;
    }

    public void setStatic(Boolean aStatic) {
        isStatic = aStatic;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Long getChangeDuration() {
        return changeDuration;
    }

    public void setChangeDuration(Long changeDuration) {
        this.changeDuration = changeDuration;
    }
}
