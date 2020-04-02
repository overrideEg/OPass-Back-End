/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.enums.attStatus;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.attendanceRules;
import com.overrideeg.apps.opass.io.valueObjects.shiftHours;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import com.overrideeg.apps.opass.utils.DateUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "name.ar", column = @Column(name = "name_ar")),
        @AttributeOverride(name = "name.en", column = @Column(name = "name_en")),
        @AttributeOverride(name = "name.tr", column = @Column(name = "name_tr")),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class workShift extends OEntity {
    @Embedded
    @JsonProperty(required = true)
    private translatedField name;
    @Embedded
    private shiftHours shiftHours;

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    public translatedField getName() {
        return name;
    }

    public void setName(translatedField name) {
        this.name = name;
    }

    public shiftHours getShiftHours() {
        return shiftHours;
    }

    public void setShiftHours(shiftHours shiftHours) {
        this.shiftHours = shiftHours;
    }


    public attStatus canLog(Date scanTime, attendanceRules attendanceRules, List<attendance> todaysWorkShiftAttendance ) {
        final DateUtils dateUtils = new DateUtils();
        final shiftHours shiftHours = getShiftHours();

        final Date lateArriveTime = dateUtils.addOrSubtractMinutes(getShiftHours().getFromHour(), attendanceRules.getAllowedLateMinutes());
        final Date maxOverTime = dateUtils.addOrSubtractHours(getShiftHours().getToHour(), attendanceRules.getMaxOverTimeHours());
        final Date minEarlyLeavyTime = dateUtils.addOrSubtractHours(getShiftHours().getToHour(), attendanceRules.getMaxOverTimeHours());


//        if (dateUtils.afterOrEqual(shiftHours.getFromHour(), scanTime)&& dateUtils.beforeOrEqual(shiftHours.getToHour(), scanTime)){
//            return att
//        }

    }


}
