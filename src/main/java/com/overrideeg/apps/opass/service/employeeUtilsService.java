/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.HR.HRPermissions;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.officialHoliday;
import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.service.HR.HRPermissionsService;
import com.overrideeg.apps.opass.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class employeeUtilsService {

    @Autowired
    HRPermissionsService permissionsService;
    @Autowired
    officialHolidayService holidayService;
    @Autowired
    workShiftService workShiftService;


    DateUtils dateUtils = new DateUtils();


    public boolean checkIfDayOff ( employee employee, LocalDate currentDate ) {
        if (!employee.getDaysOff().isEmpty()) {
            return employee.getDaysOff().contains(currentDate.getDayOfWeek().getValue());
        } else {
            List<Integer> departmentDaysOff = employee.getDepartment().getAttendanceRules().getDaysOff();
            if (!departmentDaysOff.isEmpty()) {
                return departmentDaysOff.contains(currentDate.getDayOfWeek().getValue());

            } else {
                List<Integer> branchDaysOff = employee.getBranch().getAttendanceRules().getDaysOff();
                if (!branchDaysOff.isEmpty()) {
                    return branchDaysOff.contains(currentDate.getDayOfWeek().getValue());
                }
            }
        }
        return false;
    }

    public boolean checkIfHaveAbsencePermission ( List<HRPermissions> permission, LocalDate currentDate ) {
        Date date = dateUtils.convertToDateViaInstant(currentDate, TimeZone.getTimeZone("Africa,Cairo"));
        return permission.stream().anyMatch(hrPermissions -> hrPermissions.getDate().equals(date));

    }

    public boolean checkIfOfficialHoliday ( List<officialHoliday> holidays, LocalDate currentDate ) {
        Date date = dateUtils.convertToDateViaInstant(currentDate, TimeZone.getTimeZone("Africa,Cairo"));

        return holidays.stream().anyMatch(officialHoliday ->
                officialHoliday.getFromDate().compareTo(date) >= 0 && officialHoliday.getToDate().compareTo(date) <= 0);
    }

    public workShift getWorkShiftAtDate ( employee employee, Long id, Date date ) {
        return workShiftService.getWorkShiftAtDate(id, date);
    }

    public List<HRPermissions> getPermissions ( employee employee ) {
        return permissionsService.findAll().stream()
                .filter(hrPermissions -> hrPermissions.getEmployee().getId().equals(employee.getId())).collect(Collectors.toList());
    }

    public List<officialHoliday> getHolidays () {
        return holidayService.findAll();
    }
}
