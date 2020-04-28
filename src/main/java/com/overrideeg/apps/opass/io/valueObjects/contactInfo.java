/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.valueObjects;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class contactInfo {
    private String email;

    @Column(unique = true)
    private String mobile;

    public String getEmail () {
        return email;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public String getMobile () {
        return mobile;
    }

    public void setMobile ( String mobile ) {
        this.mobile = mobile;
    }
}
