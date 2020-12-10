/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.reader;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.overrideeg.apps.opass.io.entities.employee;

import java.util.Date;

public class readerAdminRequest {
    private employee employee;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private Date date;
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private Date inTime;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private Date outTime;


    public com.overrideeg.apps.opass.io.entities.employee getEmployee () {
        return employee;
    }

    public void setEmployee ( com.overrideeg.apps.opass.io.entities.employee employee ) {
        this.employee = employee;
    }

    public Date getDate () {
        return date;
    }

    public void setDate ( Date date ) {
        this.date = date;
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
}
