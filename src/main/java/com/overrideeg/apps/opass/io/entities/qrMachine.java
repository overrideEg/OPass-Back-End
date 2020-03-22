/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class qrMachine extends OEntity {
    @ManyToOne
    @JsonProperty(required = true)
    private branch branch;
    @ManyToOne
    @JsonProperty(required = true)
    private department department;
    private Boolean isStatic;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty(required = true)
    private Date issueDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty(required = true)
    private Date expireDate;
    @Column(unique = true)
    private String macAddress;
    @JsonProperty(required = true)
    private Long changeDuration;


    public branch getBranch() {
        return branch;
    }

    public void setBranch(branch branch) {
        this.branch = branch;
    }

    public department getDepartment() {
        return department;
    }

    public void setDepartment(department department) {
        this.department = department;
    }

    public Boolean getStatic() {
        return isStatic;
    }

    public void setStatic(Boolean aStatic) {
        isStatic = aStatic;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
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
