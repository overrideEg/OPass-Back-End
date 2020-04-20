/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.valueObjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.workShift;

import java.util.Date;

public class attendanceDayByDay {
    private employee employee;
    private workShift workShift;
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date scanDate;
    private String inType;
    private String inStatus;
    @JsonFormat(pattern = "hh:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date inTime;
    private String outType;
    private String outStatus;
    @JsonFormat(pattern = "hh:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date outTime;
    private String logType;
    private String logStatus;
    @JsonFormat(pattern = "hh:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date logTime;

    public com.overrideeg.apps.opass.io.entities.employee getEmployee () {
        return employee;
    }

    public void setEmployee ( com.overrideeg.apps.opass.io.entities.employee employee ) {
        this.employee = employee;
    }

    public com.overrideeg.apps.opass.io.entities.workShift getWorkShift () {
        return workShift;
    }

    public void setWorkShift ( com.overrideeg.apps.opass.io.entities.workShift workShift ) {
        this.workShift = workShift;
    }

    public Date getScanDate () {
        return scanDate;
    }

    public void setScanDate ( Date scanDate ) {
        this.scanDate = scanDate;
    }

    public Date getInTime () {
        return inTime;
    }

    public void setInTime ( Date inTime ) {
        this.inTime = inTime;
    }

    public Date getOutTime () {
        return outTime;
    }

    public void setOutTime ( Date outTime ) {
        this.outTime = outTime;
    }

    public Date getLogTime () {
        return logTime;
    }

    public void setLogTime ( Date logTime ) {
        this.logTime = logTime;
    }

    public String getInType () {
        return inType;
    }

    public void setInType ( String inType ) {
        this.inType = inType;
    }

    public String getInStatus () {
        return inStatus;
    }

    public void setInStatus ( String inStatus ) {
        this.inStatus = inStatus;
    }

    public String getOutType () {
        return outType;
    }

    public void setOutType ( String outType ) {
        this.outType = outType;
    }

    public String getOutStatus () {
        return outStatus;
    }

    public void setOutStatus ( String outStatus ) {
        this.outStatus = outStatus;
    }

    public String getLogType () {
        return logType;
    }

    public void setLogType ( String logType ) {
        this.logType = logType;
    }

    public String getLogStatus () {
        return logStatus;
    }

    public void setLogStatus ( String logStatus ) {
        this.logStatus = logStatus;
    }
}
