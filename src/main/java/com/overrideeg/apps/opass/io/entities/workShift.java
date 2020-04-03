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


    public attendance createAttLog(employee employee, Date scanTime, attendanceRules attendanceRules, List<attendance> todayShiftLogs) {

        final DateUtils dateUtils = new DateUtils();

        final Date lateArriveTime = dateUtils.addOrSubtractMinutes(getShiftHours().getFromHour(), attendanceRules.getAllowedLateMinutes());
        final Date maxOverTime = dateUtils.addOrSubtractHours(getShiftHours().getToHour(), attendanceRules.getMaxOverTimeHours());
        final Date minEarlyLeavyTime = dateUtils.addOrSubtractHours(getShiftHours().getToHour(), attendanceRules.getMaxOverTimeHours());

        final attendance attendanceLog = new attendance(employee, this, scanTime, scanTime, attType.LOG, attStatus.normal);

        Boolean in = false;
        Boolean out = false;

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
            if (dateUtils.after(shiftHours.getFromHour(), scanTime, false, true) && dateUtils.before(lateArriveTime, scanTime, false, true)) {
                attendanceLog.setAttStatus(attStatus.normal);

                //check attending at late time
            } else if (dateUtils.after(lateArriveTime, scanTime, false, false) && dateUtils.before(minEarlyLeavyTime, scanTime, false, false)) {

                attendanceLog.setAttStatus(attStatus.lateEntrance);
            }
            //else just log
            return attendanceLog;


        //leaving state
        } else if (!out) {
            attendanceLog.setAttType(attType.OUT);


            //check leaving at normal time
            if (dateUtils.after(minEarlyLeavyTime, scanTime, false, true) && dateUtils.before(getShiftHours().getToHour(), scanTime, false, true)) {
                attendanceLog.setAttStatus(attStatus.normal);

                //check leaving at over time
            } else if (dateUtils.after(getShiftHours().getToHour(), scanTime, false, false) && dateUtils.before(maxOverTime, scanTime, false, true)) {

                attendanceLog.setAttStatus(attStatus.overTime);
            }
            //else just log
            return attendanceLog;

        }

        return attendanceLog;

    }


}
