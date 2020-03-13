/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.valueObjects;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Embeddable
public class shiftHours {
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "hh:mm:sss")
    private Date fromHours;
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "hh:mm:sss", shape = JsonFormat.Shape.STRING)
    private Date toHours;

    public Date getFromHours() {
        return fromHours;
    }

    public void setFromHours(Date fromHours) {
        this.fromHours = fromHours;
    }

    public Date getToHours() {
        return toHours;
    }

    public void setToHours(Date toHours) {
        this.toHours = toHours;
    }
}
