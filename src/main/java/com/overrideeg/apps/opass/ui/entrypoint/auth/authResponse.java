/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.auth;

import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.io.entities.branch;
import com.overrideeg.apps.opass.io.entities.department;


public class authResponse {

    private String token;
    private Users user;
    private branch branch;
    private department department;
    private Long company_id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }
}
