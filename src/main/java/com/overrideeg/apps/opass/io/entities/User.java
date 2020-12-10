/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.io.entities.HR.HRSettings;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@AttributeOverrides({
        @AttributeOverride(name = "fullName.ar", column = @Column(name = "fullName_ar")),
        @AttributeOverride(name = "fullName.en", column = @Column(name = "fullName_en")),
        @AttributeOverride(name = "fullName.tr", column = @Column(name = "fullName_tr")),
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Embedded
    private translatedField fullName;
    private String email;
    private Long company_id;
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user", targetEntity = employee.class)
    private employee employee;
    private String temporaryCode;
    private Long tenant;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = true)
    private String password;
    @Column(unique = true)
    private String macAddress;
    private String image;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
    @Transient
    private String token;
    private String fcmToken;


    @Transient
    public HRSettings settings;



    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }

    public Long getId () {
        return id;
    }

    public void setId ( Long id ) {
        this.id = id;
    }

    public String getFcmToken () {
        return fcmToken;
    }

    public void setFcmToken ( String fcmToken ) {
        this.fcmToken = fcmToken;
    }

    @Override
    public String getUsername () {
        return username;
    }

    public void setUsername ( String username ) {
        this.username = username;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = true)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = true)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = true)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = true)
    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    @Override
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public translatedField getFullName () {
        return fullName;
    }

    public void setFullName ( translatedField fullName ) {
        this.fullName = fullName;
    }

    public com.overrideeg.apps.opass.io.entities.employee getEmployee () {
        return employee;
    }

    public void setEmployee ( com.overrideeg.apps.opass.io.entities.employee employee ) {
        this.employee = employee;
    }

    public String getTemporaryCode () {
        return temporaryCode;
    }

    public void setTemporaryCode ( String temporaryCode ) {
        this.temporaryCode = temporaryCode;
    }

    public Long getTenant () {
        return tenant;
    }

    public void setTenant ( Long tenant ) {
        this.tenant = tenant;
    }


}
