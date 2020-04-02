/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestReportsService {

    @Autowired
    attendanceService attendanceService;

    public List<attendance> createAttendanceHistoryReport(Long employeeId, Integer page, Integer pageSize) {
        return attendanceService.createAttendanceHistoryReport(employeeId, page, pageSize);
    }


    public Long findAbsenceDays(Long employeeId) {
        return attendanceService.findAbsenceDays(employeeId);
    }
}
