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
import java.util.Objects;
import java.util.Optional;

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

    //TODO add maxOvertime departure time allowance🔥 🔥
    public attendance createAttLog(employee employee, Date scanDate, int scanWeekDay, attendanceRules attendanceRules, List<HRPermissions> hrPermissions, List<attendance> todayShiftLogs) {
        final DateUtils dateUtils = new DateUtils();
        final Time scanTime = dateUtils.newTime(scanDate);
        final attendance attendanceLog = new attendance(employee, this, scanDate, scanTime, attType.LOG, attStatus.logOnly);

        Date toHour;
        Date fromHour;
        Date maxOverTime;
        Date lateArriveTime;
        Date minNormalLeaveTime;
        Date maxNormalLeaveTime;

        try {
            //check if today is a custom shift day
            final Optional<customShiftHours> customShiftTime = getCustomShiftHours()
                    .stream().filter(c -> c.getDay().equals(scanWeekDay)).findFirst();

            if (customShiftTime.isPresent()) {
                //if custom shift day exist, calculate conditions from it
                fromHour = dateUtils.copyTimeToDate(scanDate, customShiftTime.get().getFromHour());
                toHour = dateUtils.copyTimeToDate(scanDate, customShiftTime.get().getToHour());

                maxOverTime = dateUtils.addOrSubtractHours(toHour, attendanceRules.getMaxOverTimeHours());
                lateArriveTime = dateUtils.addOrSubtractMinutes(fromHour, attendanceRules.getAllowedLateMinutes());
                maxNormalLeaveTime = dateUtils.addOrSubtractMinutes(toHour, attendanceRules.getAllowedEarlyLeaveMinutes());
                minNormalLeaveTime = dateUtils.addOrSubtractMinutes(toHour, -attendanceRules.getAllowedEarlyLeaveMinutes());

            } else {
                //if custom shift day dont exist, calculate conditions from normal work shift time
                fromHour = dateUtils.copyTimeToDate(scanDate, shiftHours.getFromHour());
                toHour = dateUtils.copyTimeToDate(scanDate, shiftHours.getToHour());

                maxOverTime = dateUtils.addOrSubtractHours(toHour, attendanceRules.getMaxOverTimeHours());
                lateArriveTime = dateUtils.addOrSubtractMinutes(fromHour, attendanceRules.getAllowedLateMinutes());
                maxNormalLeaveTime = dateUtils.addOrSubtractMinutes(toHour, attendanceRules.getAllowedEarlyLeaveMinutes());
                minNormalLeaveTime = dateUtils.addOrSubtractMinutes(toHour, -attendanceRules.getAllowedEarlyLeaveMinutes());
            }

            //if user have an hrPermission, update the shift boundaries
            if (hrPermissions!=null&&!hrPermissions.isEmpty()) {
                int allowedEarlyGoMinutes = hrPermissions.stream().mapToInt(HRPermissions::getMinutesEarlyGo).sum();
                int allowedLateMinutes = hrPermissions.stream().mapToInt(HRPermissions::getMinutesLate).sum();

                lateArriveTime = dateUtils.addOrSubtractMinutes(lateArriveTime, allowedLateMinutes);
                minNormalLeaveTime = dateUtils.addOrSubtractMinutes(minNormalLeaveTime, -allowedEarlyGoMinutes);
            }


            //check if the user registered his attendance in this work shift
            boolean userAttendedInThisWorkShift = todayShiftLogs.stream().filter(Objects::nonNull)
                    .map(attendance::getAttType).anyMatch(attType.IN::equals);
            //check if the user registered his departure in this work shift
            boolean userDepartureInThisWorkShift = todayShiftLogs.stream().filter(Objects::nonNull)
                    .map(attendance::getAttType).anyMatch(attType.OUT::equals);


            //attending state
            if (!userAttendedInThisWorkShift) {

                //check attending at normal time
                if (dateUtils.isBetweenTwoDates(fromHour, lateArriveTime, scanDate, true)) {
                    attendanceLog.setAttType(attType.IN);
                    return attendanceLog;
                    //check attending at late time
                } else if (dateUtils.isBetweenTwoDates(lateArriveTime, minNormalLeaveTime, scanDate, true)) {
                    attendanceLog.setAttStatus(attStatus.lateEntrance);
                    attendanceLog.setAttType(attType.IN);
                    return attendanceLog;
                }
                //else throw FORBIDDEN
                throw new CouldNotAttendException(ErrorMessages.COULD_NOT_ATTEND.getErrorMessage());

            } else if (!userDepartureInThisWorkShift) {//leaving state

                //check leaving at normal time
                if (dateUtils.isBetweenTwoDates(minNormalLeaveTime, maxNormalLeaveTime, scanDate, true)) {
                    attendanceLog.setAttType(attType.OUT);
                    return attendanceLog;

                } else if (dateUtils.isBetweenTwoDates(fromHour ,minNormalLeaveTime, scanDate, false)) {
                    //check leaving early
                    attendanceLog.setAttStatus(attStatus.earlyGo);
                    attendanceLog.setAttType(attType.OUT);
                    return attendanceLog;

                    //check leaving at over time
                } else if (dateUtils.isBetweenTwoDates(maxNormalLeaveTime, maxOverTime, scanDate, true)) {
                    attendanceLog.setAttStatus(attStatus.overTime);
                    attendanceLog.setAttType(attType.OUT);
                    return attendanceLog;
                }
                //else throw FORBIDDEN
                throw new CouldNotAttendException(ErrorMessages.COULD_NOT_ATTEND.getErrorMessage());

            } else {

                //user registered his attendance and departure, throw not allowed exception!!
                throw new CouldNotAttendException(ErrorMessages.COULD_NOT_ATTEND.getErrorMessage());

                //OLD APPROACH>>return attendanceLog;
            }

        } catch (Exception e) {
            //in case of internal error save the attendance log and call the logger
            System.err.println("error processing work shift:" + getId() + " attendance for employee:"
                    + employee.getId() + " " + e.getMessage());

            return attendanceLog;
        }

    }

    public Long getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Long updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
