/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.enums.attStatus;
import com.overrideeg.apps.opass.enums.attType;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.attendanceRules;
import com.overrideeg.apps.opass.io.valueObjects.shiftHours;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import com.overrideeg.apps.opass.utils.DateUtils;

import javax.persistence.*;
import java.sql.Time;
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


    public attendance createAttLog(employee employee, Date scanDate, attendanceRules attendanceRules, List<attendance> todayShiftLogs) {

        final DateUtils dateUtils = new DateUtils();
        final Time scanTime = dateUtils.newTime(scanDate);

        final Time lateArriveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(getShiftHours().getFromHour()), attendanceRules.getAllowedLateMinutes());
        final Time maxOverTime = dateUtils.addOrSubtractHours(dateUtils.newTime(getShiftHours().getToHour()), attendanceRules.getMaxOverTimeHours());
        final Time minEarlyLeaveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(getShiftHours().getToHour()), -attendanceRules.getAllowedEarlyLeaveMinutes());

        final attendance attendanceLog = new attendance(employee, this, scanDate, scanTime, attType.LOG, attStatus.normal);

        boolean in = false;
        boolean out = false;

        for (attendance shiftLog : todayShiftLogs) {
            if (shiftLog.getAttType() == attType.IN) {
                in = true;
            } else if (shiftLog.getAttType() == attType.OUT) {
                out = true;
            }
        }

        //attending state
        if (!in) {
            attendanceLog.setAttType(attType.IN);

            //check attending at normal time
            if (dateUtils.timeAfter(dateUtils.newTime(shiftHours.getFromHour()), scanTime, true) && dateUtils.timeBefore(lateArriveTime, scanTime, true)) {
                attendanceLog.setAttStatus(attStatus.normal);

                //check attending at late time
            } else if (dateUtils.timeAfter(lateArriveTime, scanTime, false) && dateUtils.timeBefore(minEarlyLeaveTime, scanTime, false)) {

                attendanceLog.setAttStatus(attStatus.lateEntrance);
            }
            //else just log
            return attendanceLog;


        //leaving state
        } else if (!out) {
            attendanceLog.setAttType(attType.OUT);


            //check leaving at normal time
            if (dateUtils.timeAfter(minEarlyLeaveTime, scanTime, true) && dateUtils.timeBefore(dateUtils.newTime( getShiftHours().getToHour()), scanTime, true)) {
                attendanceLog.setAttStatus(attStatus.normal);

                //check leaving at over time
            } else if (dateUtils.timeAfter(dateUtils.newTime( getShiftHours().getToHour()), scanTime, false) && dateUtils.timeBefore(maxOverTime, scanTime, true)) {

                attendanceLog.setAttStatus(attStatus.overTime);
            }
            //else just log
            return attendanceLog;

        }

        return attendanceLog;

    }


}
