/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.enums.attStatus;
import com.overrideeg.apps.opass.enums.attType;
import com.overrideeg.apps.opass.exceptions.CouldNotAttendException;
import com.overrideeg.apps.opass.io.details.customShiftHours;
import com.overrideeg.apps.opass.io.entities.HR.HRPermissions;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.attendanceRules;
import com.overrideeg.apps.opass.io.valueObjects.shiftHours;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import com.overrideeg.apps.opass.utils.DateUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Audited
@EntityListeners(AuditingEntityListener.class)
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
    @JsonIgnore
    private Long updateDateTime;

    @Override
    public boolean isValid () {
        return super.isValid();
    }

    public translatedField getName () {
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

    public attendance createAttLog(employee employee, Date scanDate, int scanWeekDay, attendanceRules attendanceRules, HRPermissions hrPermissions, List<attendance> todayShiftLogs) {
        final DateUtils dateUtils = new DateUtils();
        final Time scanTime = dateUtils.newTime(scanDate);
        final attendance attendanceLog = new attendance(employee, this, scanDate, scanTime, attType.LOG, attStatus.normal);

        //check if hrPermission allowed absence
        if(hrPermissions!=null&&hrPermissions.getAbsenceAllowed()){
            return attendanceLog;
        }

        Time maxOverTime;
        Time lateArriveTime;
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
                maxOverTime = dateUtils.addOrSubtractHours(dateUtils.newTime(getShiftHours().getToHour()), attendanceRules.getMaxOverTimeHours());
                lateArriveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(getShiftHours().getFromHour()), attendanceRules.getAllowedLateMinutes());
                maxNormalLeaveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(getShiftHours().getToHour()), attendanceRules.getAllowedEarlyLeaveMinutes());
                minNormalLeaveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(getShiftHours().getToHour()), -attendanceRules.getAllowedEarlyLeaveMinutes());
            } else {
                maxOverTime = dateUtils.addOrSubtractHours(dateUtils.newTime(customShiftHour.getToHour()), attendanceRules.getMaxOverTimeHours());
                lateArriveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(customShiftHour.getFromHour()), attendanceRules.getAllowedLateMinutes());
                maxNormalLeaveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(customShiftHour.getToHour()), attendanceRules.getAllowedEarlyLeaveMinutes());
                minNormalLeaveTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(customShiftHour.getToHour()), -attendanceRules.getAllowedEarlyLeaveMinutes());
            }

            if(hrPermissions!=null){
                lateArriveTime = dateUtils.addOrSubtractMinutes(lateArriveTime, hrPermissions.getMinutesLate());
                minNormalLeaveTime = dateUtils.addOrSubtractMinutes(minNormalLeaveTime, -hrPermissions.getMinutesEarlyGo());
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
                    return attendanceLog;
                    //check attending at late time
                } else if (dateUtils.timeAfter(lateArriveTime, scanTime, false) && dateUtils.timeBefore(minNormalLeaveTime, scanTime, false)) {
                    attendanceLog.setAttStatus(attStatus.lateEntrance);
                    attendanceLog.setAttType(attType.IN);
                    return attendanceLog;
                }
                //else throw FORBIDDEN
                throw new CouldNotAttendException(ErrorMessages.COULD_NOT_ATTEND.getErrorMessage());


                //leaving state
            } else if (!out) {

                //check leaving at normal time
                if (dateUtils.timeAfter(minNormalLeaveTime, scanTime, true) && dateUtils.timeBefore(maxNormalLeaveTime, scanTime, true)) {
                    attendanceLog.setAttType(attType.OUT);
                    return attendanceLog;

                    //check leaving early
                } else if (dateUtils.timeAfter(dateUtils.newTime(shiftHours.getFromHour()), scanTime, true) && dateUtils.timeBefore(minNormalLeaveTime, scanTime, false)) {
                    attendanceLog.setAttStatus(attStatus.earlyGo);
                    attendanceLog.setAttType(attType.OUT);
                    return attendanceLog;

                    //check leaving at over time
                } else if (dateUtils.timeAfter(maxNormalLeaveTime, scanTime, false) && dateUtils.timeBefore(maxOverTime, scanTime, true)) {
                    attendanceLog.setAttStatus(attStatus.overTime);
                    attendanceLog.setAttType(attType.OUT);
                    return attendanceLog;

                }

                //else throw FORBIDDEN
                throw new CouldNotAttendException(ErrorMessages.COULD_NOT_ATTEND.getErrorMessage());

            }
            return attendanceLog;

        } catch (Exception e) {
            //TODO call the logger
            return attendanceLog;
        }

    }

    public Long getUpdateDateTime () {
        return updateDateTime;
    }

    public void setUpdateDateTime ( Long updateDateTime ) {
        this.updateDateTime = updateDateTime;
    }
}
