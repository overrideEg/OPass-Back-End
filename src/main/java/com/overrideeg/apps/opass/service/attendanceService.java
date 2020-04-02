/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.io.repositories.attendanceRepo;
import com.overrideeg.apps.opass.io.repositories.impl.attendanceRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}