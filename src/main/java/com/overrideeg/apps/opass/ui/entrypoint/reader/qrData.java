/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.reader;

public class qrData {
    private Long qrMachine_id;
    private Long branch_id;
    private Long department_id;
    private Long issueDate;
    private Long expireDate;

    public qrData() {
    }

    public qrData(Long qrMachine_id, Long branch_id, Long department_id, Long issueDate, Long expireDate) {
        this.qrMachine_id = qrMachine_id;
        this.branch_id = branch_id;
        this.department_id = department_id;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
    }

    public Long getQrMachine_id() {
        return qrMachine_id;
    }

    public void setQrMachine_id(Long qrMachine_id) {
        this.qrMachine_id = qrMachine_id;
    }

    public Long getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Long branch_id) {
        this.branch_id = branch_id;
    }

    public Long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Long department_id) {
        this.department_id = department_id;
    }

    public Long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Long issueDate) {
        this.issueDate = issueDate;
    }

    public Long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Long expireDate) {
        this.expireDate = expireDate;
    }
}
