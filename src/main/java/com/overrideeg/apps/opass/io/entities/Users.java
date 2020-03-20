/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.*;
import com.overrideeg.apps.opass.enums.userType;
import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users extends OEntity {
    private String userId;
    @Column(nullable = false, unique = true, length = 15)
    private String userName;
    @JsonIgnore
    private String encryptedPassword;
    private String email;
    @JsonIgnore
    private String salt;
    @JsonIgnore
    private String token;
    @Enumerated(EnumType.STRING)
    private userType userType;
    @ManyToOne
    @JsonFormat(lenient = OptBoolean.TRUE)
    private employee employee;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = true)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String macAddress;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public com.overrideeg.apps.opass.enums.userType getUserType() {
        return userType;
    }

    public void setUserType(com.overrideeg.apps.opass.enums.userType userType) {
        this.userType = userType;
    }

    public com.overrideeg.apps.opass.io.entities.employee getEmployee() {
        return employee;
    }

    public void setEmployee(com.overrideeg.apps.opass.io.entities.employee employee) {
        this.employee = employee;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
