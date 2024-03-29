/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6986746375915710855L;
    private String username;
    private String password;
    private String macAddress;
    private String fcmToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMacAddress () {
        return macAddress;
    }

    public void setMacAddress ( String macAddress ) {
        this.macAddress = macAddress;
    }

    public String getFcmToken () {
        return fcmToken;
    }

    public void setFcmToken ( String fcmToken ) {
        this.fcmToken = fcmToken;
    }
}
