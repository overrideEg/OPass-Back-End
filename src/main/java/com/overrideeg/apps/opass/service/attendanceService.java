/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.io.repositories.attendanceRepo;
import com.overrideeg.apps.opass.io.repositories.impl.attendanceRepoImpl;
import com.overrideeg.apps.opass.ui.entrypoint.reports.valueObjects.countAttendanceInDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class attendanceService extends AbstractService<attendance> {

    public attendanceService(final attendanceRepo inRepository) {
        super(inRepository);
    }

    @Autowired
    attendanceRepoImpl attendanceRepo;


    public List<attendance> employeeTodaysShitLogs(employee employee, Date currentDate, workShift currentShift) {
        return attendanceRepo.findEmployeeTodaysShitLogs(employee, currentDate, currentShift);
    }

    public List<attendance> createAttendanceHistoryReport(Long employee, Integer page, Integer pageSize) {
        return attendanceRepo.createAttendanceHistoryReport(employee, page, pageSize);
    }

    public Long findAbsenceDays(Long employee) {
        return attendanceRepo.findAbsenceDays(employee);
    }

    public List<countAttendanceInDate> findTotalEmployeeAttendanceFromFirstMonth(Date fromDate, Date toDate) {
        List result = attendanceRepo.findTotalEmployeeAttendanceFromFirstMonth(fromDate, toDate);
        List<countAttendanceInDate> returnValue = new ArrayList<>();
        result.forEach(res -> {
            Object Date = Array.get(res, 0);
            countAttendanceInDate attendance = new countAttendanceInDate();
            attendance.setDate(Date.toString());
            Object count = Array.get(res, 1);
            attendance.setCount((Long) count);
            returnValue.add(attendance);
        });


        return returnValue;
    }
}