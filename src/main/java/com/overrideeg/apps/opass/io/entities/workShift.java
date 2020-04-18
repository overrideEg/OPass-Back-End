/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.enums.attStatus;
import com.overrideeg.apps.opass.enums.attType;
import com.overrideeg.apps.opass.io.details.customShiftHours;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.attendanceRules;
import com.overrideeg.apps.opass.io.valueObjects.shiftHours;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import com.overrideeg.apps.opass.utils.DateUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<customShiftHours> customShiftHours;

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


    public List<com.overrideeg.apps.opass.io.details.customShiftHours> getCustomShiftHours() {
        return customShiftHours;
    }

    public void setCustomShiftHours(List<com.overrideeg.apps.opass.io.details.customShiftHours> customShiftHours) {
        this.customShiftHours = customShiftHours;
    }

    public attendance createAttLog(employee employee, Date scanDate, int scanWeekDay, attendanceRules attendanceRules, List<attendance> todayShiftLogs) {
        final DateUtils dateUtils = new DateUtils();
        final Time scanTime = dateUtils.newTime(scanDate);
        final attendance attendanceLog = new attendance(employee, this, scanDate, scanTime, attType.LOG, attStatus.normal);

        Time lateArriveTime;
        Time maxOverTime;
        Time minNormalLeaveTime;
        Time maxNormalLeaveTime;

        try {

            customShiftHours customShiftHour = new customShiftHours();

            //check if today is a custom shift day
            if (!getCustomShiftHours().isEmpty()) {

                for (customShiftHours customShiftHours : getCustomShiftHours()) {
                    if (customShiftHours.getDay().equals(scanWeekDay)) {
                        customShiftHour = customShiftHours;
                        break;
                    }
                }

            }

            if (customShiftHour.getId() == null) {

                lateArriveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(getShiftHours().getFromHour()), attendanceRules.getAllowedLateMinutes());
                maxOverTime = dateUtils.addOrSubtractHours(dateUtils.newTime(getShiftHours().getToHour()), attendanceRules.getMaxOverTimeHours());
                minNormalLeaveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(getShiftHours().getToHour()), -attendanceRules.getAllowedEarlyLeaveMinutes());
                maxNormalLeaveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(getShiftHours().getToHour()), attendanceRules.getAllowedEarlyLeaveMinutes());

            } else {

                lateArriveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(customShiftHour.getFromHour()), attendanceRules.getAllowedLateMinutes());
                maxOverTime = dateUtils.addOrSubtractHours(dateUtils.newTime(customShiftHour.getToHour()), attendanceRules.getMaxOverTimeHours());
                minNormalLeaveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(customShiftHour.getToHour()), -attendanceRules.getAllowedEarlyLeaveMinutes());
                maxNormalLeaveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(customShiftHour.getToHour()), attendanceRules.getAllowedEarlyLeaveMinutes());

            }

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

                //check attending at normal time
                if (dateUtils.timeAfter(dateUtils.newTime(shiftHours.getFromHour()), scanTime, true) && dateUtils.timeBefore(lateArriveTime, scanTime, true)) {
                    attendanceLog.setAttType(attType.IN);
                    attendanceLog.setAttStatus(attStatus.normal);

                    //check attending at late time
                } else if (dateUtils.timeAfter(lateArriveTime, scanTime, false) && dateUtils.timeBefore(minNormalLeaveTime, scanTime, false)) {
                    attendanceLog.setAttType(attType.IN);

                    attendanceLog.setAttStatus(attStatus.lateEntrance);
                }
                //else just log
                return attendanceLog;


                //leaving state
            } else if (!out) {

                //check leaving at normal time
                if (dateUtils.timeAfter(minNormalLeaveTime, scanTime, true) && dateUtils.timeBefore(maxNormalLeaveTime, scanTime, true)) {
                    attendanceLog.setAttType(attType.OUT);
                    attendanceLog.setAttStatus(attStatus.normal);

                    //check leaving early
                }

                // todo replace with go permissions
//                else if (dateUtils.timeAfter(dateUtils.newTime(shiftHours.getFromHour()), scanTime, true) && dateUtils.timeBefore(minNormalLeaveTime, scanTime, false)) {
//                    attendanceLog.setAttType(attType.OUT);
//                    attendanceLog.setAttStatus(attStatus.earlyGo);
//
//                    //check leaving at over time
//                }

                else if (dateUtils.timeAfter(maxNormalLeaveTime, scanTime, false) && dateUtils.timeBefore(maxOverTime, scanTime, true)) {
                    attendanceLog.setAttType(attType.OUT);
                    attendanceLog.setAttStatus(attStatus.overTime);

                }

                //else just log
                return attendanceLog;

            }
            return attendanceLog;

        } catch (Exception e) {
            return attendanceLog;

        }

    }


}
