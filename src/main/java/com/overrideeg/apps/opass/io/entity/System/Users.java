package com.overrideeg.apps.opass.io.entity.System;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users extends OEntity {
    @Column(nullable = false, unique = true, length = 32)
    private String userName;
    private String email;
    @Column(nullable = false, length = 16)
    private String password;
    private String type;

    @PrePersist
    public void PrePersist() {
        if (type.equals(null))
            type = "Customer";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
