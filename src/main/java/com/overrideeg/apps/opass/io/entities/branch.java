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
public class branch extends OEntity {
    @Embedded
    @JsonProperty(required = true)
    private translatedField name;
    private String phoneNumber;
    @Embedded
    private com.overrideeg.apps.opass.io.valueObjects.attendanceRules attendanceRules;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public com.overrideeg.apps.opass.io.valueObjects.attendanceRules getAttendanceRules() {
        return attendanceRules;
    }

    public void setAttendanceRules(com.overrideeg.apps.opass.io.valueObjects.attendanceRules attendanceRules) {
        this.attendanceRules = attendanceRules;
    }
}
