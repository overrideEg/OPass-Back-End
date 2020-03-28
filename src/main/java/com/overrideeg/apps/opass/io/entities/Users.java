/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String salt;
    @JsonIgnore
    private String token;
    @Enumerated(EnumType.STRING)
    private com.overrideeg.apps.opass.enums.userType userType;
    private Long company_id;
    @ManyToOne(fetch = FetchType.EAGER)
    private employee employee;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = true)
    private String password;
    @Column(unique = true)
    private String macAddress;
    private String image;

    @Override
    public boolean isValid() {
        return super.isValid();
    }

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

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
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

    public com.overrideeg.apps.opass.io.entities.employee getEmployee() {
        return employee;
    }

    public void setEmployee(com.overrideeg.apps.opass.io.entities.employee employee) {
        this.employee = employee;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
