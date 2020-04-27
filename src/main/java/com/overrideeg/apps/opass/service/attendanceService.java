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
import com.overrideeg.apps.opass.io.valueObjects.attendanceReport;
import com.overrideeg.apps.opass.ui.entrypoint.reports.valueObjects.countAttendanceInDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class attendanceService extends AbstractService<attendance> {

    public attendanceService(final attendanceRepo inRepository) {
        super(inRepository);
    }

    @Autowired
    attendanceRepoImpl attendanceRepo;

    @Override
    public List<attendance> findAll(int start, int limit) {
        return attendanceRepo.findAll(start, limit);
    }

    public List<attendanceReport> findAttendanceReport(int start, int limit) {
        List<attendance> all = attendanceRepo.findAll(start, limit);
        return getAttendanceReports(all);
    }

    public List<attendanceReport> findAttendanceReportBetweenTwoDates(Date fromDate, Date toDate) {
        List<attendance> all = attendanceRepo.findAttendanceBetweenTwoDates(fromDate, toDate);
        return getAttendanceReports(all);
    }

    private List<attendanceReport> getAttendanceReports(List<attendance> all) {
        List<attendanceReport> dayByDays = new ArrayList<>();
        all.forEach(attendance -> {
            attendanceReport dayByDay = new attendanceReport();
            if (attendance.getAttType().equals(attType.IN)) {
                Optional<attendanceReport> anyRecord = findRecord(dayByDays, attendance);
                if (anyRecord.isPresent()) {
                    attendanceReport record = anyRecord.get();
                    record.setInType(attendance.getAttType().name());
                    record.setInStatus(attendance.getAttStatus().name());
                    record.setWorkShift(attendance.getWorkShift());
                    record.setInId(attendance.getId());
                    record.setInTime(attendance.getScanTime());
                } else {
                    dayByDay.setInTime(attendance.getScanTime());
                    dayByDay.setInStatus(attendance.getAttStatus().name());
                    dayByDay.setInType(attendance.getAttType().name());
                    dayByDay.setEmployee(attendance.getEmployee());
                    dayByDay.setWorkShift(attendance.getWorkShift());
                    dayByDay.setScanDate(attendance.getScanDate());
                    dayByDay.setInId(attendance.getId());

                    dayByDays.add(dayByDay);
                }
            }
            if (attendance.getAttType().equals(attType.OUT)) {
                Optional<attendanceReport> anyRecord = findRecord(dayByDays, attendance);

                if (anyRecord.isPresent()) {
                    attendanceReport record = anyRecord.get();
                    record.setOutType(attendance.getAttType().name());
                    record.setOutStatus(attendance.getAttStatus().name());
                    record.setOutTime(attendance.getScanTime());
                    record.setWorkShift(attendance.getWorkShift());
                    record.setOutId(attendance.getId());

                } else {
                    dayByDay.setOutTime(attendance.getScanTime());
                    dayByDay.setOutStatus(attendance.getAttStatus().name());
                    dayByDay.setOutType(attendance.getAttType().name());
                    dayByDay.setEmployee(attendance.getEmployee());
                    dayByDay.setWorkShift(attendance.getWorkShift());
                    dayByDay.setScanDate(attendance.getScanDate());
                    dayByDay.setOutId(attendance.getId());

                    dayByDays.add(dayByDay);
                }
            }
            if (attendance.getAttType().equals(attType.LOG)) {
                Optional<attendanceReport> anyRecord = findRecord(dayByDays, attendance);
                if (anyRecord.isPresent() && anyRecord.get().getLogStatus() == null) {
                    attendanceReport record = anyRecord.get();
                    record.setLogType(attendance.getAttType().name());
                    record.setLogStatus(attendance.getAttStatus().name());
                    record.setWorkShift(attendance.getWorkShift());
                    record.setLogTime(attendance.getScanTime());
                    record.setLogId(attendance.getId());
                } else {
                    dayByDay.setLogTime(attendance.getScanTime());
                    dayByDay.setLogStatus(attendance.getAttStatus().name());
                    dayByDay.setLogType(attendance.getAttType().name());
                    dayByDay.setEmployee(attendance.getEmployee());
                    dayByDay.setWorkShift(attendance.getWorkShift());
                    dayByDay.setScanDate(attendance.getScanDate());
                    dayByDay.setLogId(attendance.getId());
                    dayByDays.add(dayByDay);
                }
            }

        });

        return dayByDays;
    }


    private Optional<attendanceReport> findRecord(List<attendanceReport> dayByDays, attendance attendance) {
        return dayByDays.stream()
                .filter(day -> day.getEmployee() != null && day.getEmployee().getId().equals(attendance.getEmployee().getId()))
                .filter(day -> day.getScanDate().equals(attendance.getScanDate())).findFirst();
    }

    public List<attendance> employeeTodaysLogs(employee employee, Date currentDate, boolean sortByreverse) {
        List<attendance> employeeTodaysLogs = attendanceRepo.findEmployeeTodaysLogs(employee, currentDate);

        if (sortByreverse) {

            employeeTodaysLogs.sort(Comparator.comparing(attendance::getScanTime).reversed());

            return employeeTodaysLogs;

        }

        return employeeTodaysLogs;
    }

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