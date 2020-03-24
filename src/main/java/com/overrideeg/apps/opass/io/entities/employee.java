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
        @AttributeOverride(name = "name.en", column = @Column(name = "name_en")),
        @AttributeOverride(name = "name.tr", column = @Column(name = "name_tr")),
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
    @Column(unique = true)
    @JsonProperty(required = true, access = JsonProperty.Access.READ_WRITE)
    private String ssn;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date birthDate;
    @Embedded
    private contactInfo contactInfo;
    private Boolean attendanceException;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date contractStartDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date contractEndDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date firingDate;
    @OneToOne(fetch = FetchType.EAGER)
    @JsonProperty(required = true)
    private workShift shift;
    @Enumerated(EnumType.STRING)
    private employeeStatus status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long createdUserId;

    public translatedField getName() {
        return name;
    }

    public void setName(translatedField name) {
        this.name = name;
    }

    public department getDepartment() {
        return department;
    }

    public void setDepartment(department department) {
        this.department = department;
    }

    public branch getBranch() {
        return branch;
    }

    public void setBranch(branch branch) {
        this.branch = branch;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public contactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(contactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Boolean getAttendanceException() {
        return attendanceException;
    }

    public void setAttendanceException(Boolean attendanceException) {
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

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }
}
