/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.auth.model;

public class changePassword {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String tempCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword () {
        return newPassword;
    }

    public void setNewPassword ( String newPassword ) {
        this.newPassword = newPassword;
    }

    public String getTempCode () {
        return tempCode;
    }

    public void setTempCode ( String tempCode ) {
        this.tempCode = tempCode;
    }
}
