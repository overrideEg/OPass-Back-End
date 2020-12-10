/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import com.overrideeg.apps.opass.io.valueObjects.translatedLob;

import javax.persistence.*;
import java.util.List;
import java.util.TimeZone;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "name.ar", column = @Column(name = "name_ar")),
        @AttributeOverride(name = "name.en", column = @Column(name = "name_en")),
        @AttributeOverride(name = "name.tr", column = @Column(name = "name_tr"))
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class company extends OEntity {

    @Embedded
    @JsonProperty(required = true)
    private translatedField name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true, nullable = false)
    private String database_name;
    private Boolean enabled;
    private String image;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> subscriptions;
    @ManyToOne
    private country country;
    private TimeZone timeZone;
    @ManyToOne
    private company parentCompany;



    @Override
    public boolean isValid () {
        return super.isValid();
    }


    public translatedField getName () {
        return name;
    }

    public void setName ( translatedField name ) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String website ) {
        this.email = website;
    }

    public String getPhoneNumber () {
        return phoneNumber;
    }

    public void setPhoneNumber ( String phoneNumber ) {
        this.phoneNumber = phoneNumber;
    }

    public com.overrideeg.apps.opass.io.entities.country getCountry () {
        return country;
    }

    public void setCountry ( com.overrideeg.apps.opass.io.entities.country country ) {
        this.country = country;
    }

    public String getDatabase_name () {
        return database_name;
    }

    public void setDatabase_name ( String database_name ) {
        this.database_name = database_name;
    }

    public Boolean getEnabled () {
        return enabled;
    }

    public void setEnabled ( Boolean enabled ) {
        this.enabled = enabled;
    }


    public String getImage () {
        return image;
    }

    public void setImage ( String image ) {
        this.image = image;
    }

    public List<String> getSubscriptions () {
        return subscriptions;
    }

    public void setSubscriptions ( List<String> subscriptions ) {
        this.subscriptions = subscriptions;
    }

    public TimeZone getTimeZone () {
        return timeZone;
    }

    public void setTimeZone ( TimeZone timeZone ) {
        this.timeZone = timeZone;
    }

    public company getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(company parentCompany) {
        this.parentCompany = parentCompany;
    }
}
