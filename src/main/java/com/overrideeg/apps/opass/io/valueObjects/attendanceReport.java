/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.valueObjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.workShift;

import java.util.Date;

public class attendanceReport {
    private employee employee;
    private workShift workShift;
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date scanDate;
    private String inType;
    private String inStatus;
    @JsonFormat(pattern = "hh:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date inTime;
    private Long inId;
    private String outType;
    private String outStatus;
    @JsonFormat(pattern = "hh:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date outTime;
    private Long outId;
    private String logType;
    private String logStatus;
    @JsonFormat(pattern = "hh:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date logTime;
    private Long LogId;

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

    public Date getInTime () {
        return inTime;
    }

    public void setInTime ( Date inTime ) {
        this.inTime = inTime;
    }

    public Long getInId () {
        return inId;
    }

    public void setInId ( Long inId ) {
        this.inId = inId;
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

    public Date getOutTime () {
        return outTime;
    }

    public void setOutTime ( Date outTime ) {
        this.outTime = outTime;
    }

    public Long getOutId () {
        return outId;
    }

    public void setOutId ( Long outId ) {
        this.outId = outId;
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

    public Date getLogTime () {
        return logTime;
    }

    public void setLogTime ( Date logTime ) {
        this.logTime = logTime;
    }

    public Long getLogId () {
        return LogId;
    }

    public void setLogId ( Long logId ) {
        LogId = logId;
    }
}
