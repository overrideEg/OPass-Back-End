/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.valueObjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Embeddable
public class shiftHours {
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "hh:mm:ss", lenient = OptBoolean.TRUE, shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date fromHour;
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "hh:mm:ss", lenient = OptBoolean.TRUE, with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date toHour;

    public Date getFromHour() {
        return fromHour;
    }

    public void setFromHour(Date fromHour) {
        this.fromHour = fromHour;
    }

    public Date getToHour() {
        return toHour;
    }

    public void setToHour(Date toHour) {
        this.toHour = toHour;
    }
}
