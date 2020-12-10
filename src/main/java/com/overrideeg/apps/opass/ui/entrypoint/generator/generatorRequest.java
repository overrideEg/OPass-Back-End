/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.generator;

import com.overrideeg.apps.opass.io.entities.branch;
import com.overrideeg.apps.opass.io.entities.department;
import com.overrideeg.apps.opass.io.entities.qrMachine;

public class generatorRequest {
    private branch branch;
    private department department;
    private qrMachine qrMachine;
    private String macAddress;

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

    public com.overrideeg.apps.opass.io.entities.qrMachine getQrMachine() {
        return qrMachine;
    }

    public void setQrMachine(com.overrideeg.apps.opass.io.entities.qrMachine qrMachine) {
        this.qrMachine = qrMachine;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
