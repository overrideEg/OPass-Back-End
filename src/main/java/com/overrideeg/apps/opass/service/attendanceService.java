/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.enums.attType;
import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.io.repositories.attendanceRepo;
import com.overrideeg.apps.opass.io.repositories.impl.attendanceRepoImpl;
import com.overrideeg.apps.opass.io.valueObjects.attendanceDayByDay;
import com.overrideeg.apps.opass.ui.entrypoint.reports.valueObjects.countAttendanceInDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class attendanceService extends AbstractService<attendance> {

    public attendanceService ( final attendanceRepo inRepository ) {
        super(inRepository);
    }

    @Autowired
    attendanceRepoImpl attendanceRepo;

    @Override
    public List<attendance> findAll ( int start, int limit ) {
        return attendanceRepo.findAll(start, limit);
    }

    public List<attendanceDayByDay> findAllDayByDay ( int start, int limit ) {
        List<attendance> all = attendanceRepo.findAll(start, limit);

        List<attendanceDayByDay> dayByDays = new ArrayList<>();
        all.forEach(attendance -> {
            attendanceDayByDay dayByDay = new attendanceDayByDay();
            if (attendance.getAttType().equals(attType.IN)) {
                Optional<attendanceDayByDay> anyRecord = findRecord(dayByDays, attendance);
                if (anyRecord.isPresent()) {
                    attendanceDayByDay record = anyRecord.get();
                    record.setInType(attendance.getAttType().name());
                    record.setInStatus(attendance.getAttStatus().name());
                    record.setWorkShift(attendance.getWorkShift());
                    record.setInTime(attendance.getScanTime());
                } else {
                    dayByDay.setInTime(attendance.getScanTime());
                    dayByDay.setInStatus(attendance.getAttStatus().name());
                    dayByDay.setInType(attendance.getAttType().name());
                    dayByDay.setEmployee(attendance.getEmployee());
                    dayByDay.setWorkShift(attendance.getWorkShift());
                    dayByDay.setScanDate(attendance.getScanDate());
                    dayByDays.add(dayByDay);
                }
            }
            if (attendance.getAttType().equals(attType.OUT)) {
                Optional<attendanceDayByDay> anyRecord = findRecord(dayByDays, attendance);

                if (anyRecord.isPresent()) {
                    attendanceDayByDay record = anyRecord.get();
                    record.setOutType(attendance.getAttType().name());
                    record.setOutStatus(attendance.getAttStatus().name());
                    record.setOutTime(attendance.getScanTime());
                    record.setWorkShift(attendance.getWorkShift());
                } else {
                    dayByDay.setOutTime(attendance.getScanTime());
                    dayByDay.setOutStatus(attendance.getAttStatus().name());
                    dayByDay.setOutType(attendance.getAttType().name());
                    dayByDay.setEmployee(attendance.getEmployee());
                    dayByDay.setWorkShift(attendance.getWorkShift());
                    dayByDay.setScanDate(attendance.getScanDate());
                    dayByDays.add(dayByDay);
                }
            }
            if (attendance.getAttType().equals(attType.LOG)) {
                Optional<attendanceDayByDay> anyRecord = findRecord(dayByDays, attendance);
                if (anyRecord.isPresent()) {
                    attendanceDayByDay record = anyRecord.get();
                    record.setLogType(attendance.getAttType().name());
                    record.setLogStatus(attendance.getAttStatus().name());
                    record.setWorkShift(attendance.getWorkShift());
                    record.setLogTime(attendance.getScanTime());
                } else {
                    dayByDay.setLogTime(attendance.getScanTime());
                    dayByDay.setLogStatus(attendance.getAttStatus().name());
                    dayByDay.setLogType(attendance.getAttType().name());
                    dayByDay.setEmployee(attendance.getEmployee());
                    dayByDay.setWorkShift(attendance.getWorkShift());
                    dayByDay.setScanDate(attendance.getScanDate());
                    dayByDays.add(dayByDay);
                }
            }

        });

        return dayByDays;
    }

    private Optional<attendanceDayByDay> findRecord ( List<attendanceDayByDay> dayByDays, attendance attendance ) {
        return dayByDays.stream()
                .filter(day -> day.getEmployee() != null && day.getEmployee().getId().equals(attendance.getEmployee().getId()))
                .filter(day -> day.getScanDate().equals(attendance.getScanDate())).findFirst();
    }

    public List<attendance> employeeTodaysShitLogs ( employee employee, Date currentDate, workShift currentShift ) {
        return attendanceRepo.findEmployeeTodaysShitLogs(employee, currentDate, currentShift);
    }

    public List<attendance> createAttendanceHistoryReport ( Long employee, Integer page, Integer pageSize ) {
        return attendanceRepo.createAttendanceHistoryReport(employee, page, pageSize);
    }

    public Long findAbsenceDays ( Long employee ) {
        return attendanceRepo.findAbsenceDays(employee);
    }

    public List<countAttendanceInDate> findTotalEmployeeAttendanceFromFirstMonth ( Date fromDate, Date toDate ) {
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