/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.enums.attType;
import com.overrideeg.apps.opass.enums.employeeStatus;
import com.overrideeg.apps.opass.enums.gender;
import com.overrideeg.apps.opass.enums.userType;
import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.attendanceRules;
import com.overrideeg.apps.opass.io.valueObjects.contactInfo;
import com.overrideeg.apps.opass.io.valueObjects.shiftHours;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import com.overrideeg.apps.opass.service.attendanceService;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
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
public class employee extends OEntity {
    @Embedded
    @JsonProperty(required = true)
    private translatedField name;
    @ManyToOne
    @JsonProperty(required = true)
    private department department;
    @ManyToOne
    @JsonProperty(required = true)
    private branch branch;
    @Column(unique = true)
    @JsonProperty(required = true, access = JsonProperty.Access.READ_WRITE)
    private String ssn;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date birthDate;
    @Embedded
    private contactInfo contactInfo;
    private Boolean attendanceException;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date contractStartDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date contractEndDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date firingDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_shifts",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "workshift_id")
            , uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "workshift_id"})})
    @Fetch(FetchMode.SUBSELECT)
    private List<workShift> shifts;
    @Enumerated(EnumType.STRING)
    private employeeStatus status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long createdUserId;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<userType> userType;
    private Double salary;
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> daysOff;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_optional_branches",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
            , uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "branch_id"})})
    @Fetch(FetchMode.SUBSELECT)
    private List<branch> optionalBranches;
    @Enumerated(EnumType.STRING)
    private gender gender;
    // todo add in service @amr ahmed

    @Override
    public boolean isValid () {
        return super.isValid();
    }

    public translatedField getName () {
        return name;
    }

    public void setName ( translatedField name ) {
        this.name = name;
    }

    public department getDepartment () {
        return department;
    }

    public void setDepartment ( department department ) {
        this.department = department;
    }

    public branch getBranch () {
        return branch;
    }

    public void setBranch ( branch branch ) {
        this.branch = branch;
    }

    public String getSsn () {
        return ssn;
    }

    public void setSsn ( String ssn ) {
        this.ssn = ssn;
    }

    public Date getBirthDate () {
        return birthDate;
    }

    public void setBirthDate ( Date birthDate ) {
        this.birthDate = birthDate;
    }

    public contactInfo getContactInfo () {
        return contactInfo;
    }

    public void setContactInfo ( contactInfo contactInfo ) {
        this.contactInfo = contactInfo;
    }

    public Boolean getAttendanceException () {
        return attendanceException;
    }

    public void setAttendanceException ( Boolean attendanceException ) {
        this.attendanceException = attendanceException;
    }

    public Date getContractStartDate () {
        return contractStartDate;
    }

    public void setContractStartDate ( Date contractStartDate ) {
        this.contractStartDate = contractStartDate;
    }

    public Date getContractEndDate () {
        return contractEndDate;
    }

    public void setContractEndDate ( Date contractEndDate ) {
        this.contractEndDate = contractEndDate;
    }

    public Date getFiringDate () {
        return firingDate;
    }

    public void setFiringDate ( Date firingDate ) {
        this.firingDate = firingDate;
    }

    public List<workShift> getShifts () {
        return shifts;
    }

    public void setShifts ( List<workShift> shifts ) {
        this.shifts = shifts;
    }

    public employeeStatus getStatus () {
        return status;
    }

    public void setStatus ( employeeStatus status ) {
        this.status = status;
    }

    public Long getCreatedUserId () {
        return createdUserId;
    }

    public void setCreatedUserId ( Long createdUserId ) {
        this.createdUserId = createdUserId;
    }


    public List<com.overrideeg.apps.opass.enums.userType> getUserType () {
        return userType;
    }

    public void setUserType ( List<com.overrideeg.apps.opass.enums.userType> userType ) {
        this.userType = userType;
    }

    public Double getSalary () {
        return salary;
    }

    public void setSalary ( Double salary ) {
        this.salary = salary;
    }

    public List<Integer> getDaysOff () {
        return daysOff;
    }

    public void setDaysOff ( List<Integer> daysOff ) {
        this.daysOff = daysOff;
    }

    public List<branch> getOptionalBranches () {
        return optionalBranches;
    }

    public void setOptionalBranches ( List<branch> optionalBranches ) {
        this.optionalBranches = optionalBranches;
    }

    public com.overrideeg.apps.opass.enums.gender getGender () {
        return gender;
    }

    public void setGender ( com.overrideeg.apps.opass.enums.gender gender ) {
        this.gender = gender;
    }

    /**
     * helper method to determine employee current work shift
     * loops through emp work shifts.. and filter shifts that meet the scanTime
     * drop current shift and look for the second one that meet the current time if user attended and left in this current shift
     * TODO resolve two continuous shifts (add overtime)
     * TODO resolve TwoDays shift (11pm-7am)
     * TODO resolve holidays
     */
    public workShift getCurrentWorkShift ( attendanceService attendanceService, Date scanDate, List<workShift> workShifts, attendanceRules attendanceRules ) {

        final DateUtils dateUtils = new DateUtils();

        for (workShift workShift : workShifts) {

            final shiftHours shiftTime = workShift.getShiftHours();


            final Time shiftStartLeavingTime = dateUtils.addOrSubtractMinutes(dateUtils.newTime(shiftTime.getToHour()), -attendanceRules.getAllowedEarlyLeaveMinutes());
            final Time shiftEndTimeWithAllowedMinuets = dateUtils.addOrSubtractMinutes(dateUtils.newTime(shiftTime.getToHour()), attendanceRules.getAllowedLateLeaveMinutes());


            final boolean currentShift = dateUtils.isBetweenTwoTimes(dateUtils.newTime(shiftTime.getFromHour()), shiftEndTimeWithAllowedMinuets, dateUtils.newTime(scanDate));

            if (currentShift) {

                boolean left = false;
                boolean attended = false;
                final List<attendance> todayShiftLogs = attendanceService.employeeTodaysShitLogs(this, scanDate, workShift);

                for (attendance shiftLog : todayShiftLogs) {
                    if (shiftLog.getAttType() == attType.IN) {
                        attended = true;
                    } else if (shiftLog.getAttType() == attType.OUT) {
                        left = true;
                    }
                }

                if ((!attended && dateUtils.timeBefore(shiftStartLeavingTime, dateUtils.newTime(scanDate), false)) || !left) {
                    return workShift;
                }

            }
        }
        return null;

    }

    /**
     * helper method to determine employee current Attendance Rules
     */
    public attendanceRules fetchEmployeeAttRules () {

        if (getDepartment().getAttendanceRules() != null) {
            return getDepartment().getAttendanceRules();
        }

        if (getBranch().getAttendanceRules() != null) {
            return getDepartment().getAttendanceRules();
        }

//        if (getBranch().getAttendanceRules() != null) {
//            return getDepartment().getAttendanceRules();
//        }
        throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()); //TODO better naming for exceptions

    }


}
