/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.valueObjects;

public class AttendanceHistory {
    private Long timestamp;
    private translatedField shiftName;
    private String type;
    private String status;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public translatedField getShiftName() {
        return shiftName;
    }

    public void setShiftName(translatedField shiftName) {
        this.shiftName = shiftName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
