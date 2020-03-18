/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.enums.employeeStatus;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.contactInfo;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;

import javax.persistence.*;
import java.util.Date;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "name.ar", column = @Column(name = "name_ar")),
        @AttributeOverride(name = "name.en", column = @Column(name = "name_en"))
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class employee extends OEntity {
    @Embedded
    @JsonProperty(required = true)
    private translatedField name;
    @ManyToOne
    @JsonProperty(required = true)
    private department department;
    @ManyToOne
    @JsonProperty(required = true)
    private branch branch;
    private Integer SSN;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy, hh:mm:ss a", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date birthDate;
    @Embedded
    private contactInfo contactInfo;
    private Boolean attendanceException;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy, hh:mm:ss a", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date contractStartDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy, hh:mm:ss a", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date contractEndDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy, hh:mm:ss a", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date firingDate;
    @OneToOne(fetch = FetchType.EAGER)
    private workShift shift;
    @Enumerated(EnumType.STRING)
    private employeeStatus status;

    public translatedField getName() {
        return name;
    }

    public void setName(translatedField name) {
        this.name = name;
    }

    public com.overrideeg.apps.opass.io.entities.department getDepartment() {
        return department;
    }

    public void setDepartment(com.overrideeg.apps.opass.io.entities.department department) {
        this.department = department;
    }

    public com.overrideeg.apps.opass.io.entities.branch getBranch() {
        return branch;
    }

    public void setBranch(com.overrideeg.apps.opass.io.entities.branch branch) {
        this.branch = branch;
    }

    public Integer getSSN() {
        return SSN;
    }

    public void setSSN(Integer SSN) {
        this.SSN = SSN;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public com.overrideeg.apps.opass.io.valueObjects.contactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(com.overrideeg.apps.opass.io.valueObjects.contactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public boolean isAttendanceException() {
        return attendanceException;
    }

    public void setAttendanceException(boolean attendanceException) {
        this.attendanceException = attendanceException;
    }

    public Date getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public Date getFiringDate() {
        return firingDate;
    }

    public void setFiringDate(Date firingDate) {
        this.firingDate = firingDate;
    }

    public workShift getShift() {
        return shift;
    }

    public void setShift(workShift shift) {
        this.shift = shift;
    }

    public employeeStatus getStatus() {
        return status;
    }

    public void setStatus(employeeStatus status) {
        this.status = status;
    }
}
