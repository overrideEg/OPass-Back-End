/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.valueObjects.AttendanceHistory;
import com.overrideeg.apps.opass.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RestReportsService {

    @Autowired
    attendanceService attendanceService;


    public List<AttendanceHistory> createAttendanceHistoryReport(Long employeeId, Integer page, Integer pageSize) {
        List<attendance> attendanceList = attendanceService.createAttendanceHistoryReport(employeeId, page, pageSize);
        List<AttendanceHistory> attendanceHistories = new ArrayList<>();
        attendanceList.forEach(attend -> {
            AttendanceHistory history = new AttendanceHistory();
            if (attend.getWorkShift() != null)
                history.setShiftName(attend.getWorkShift().getName());
            history.setStatus(attend.getAttStatus().toString());
            history.setType(attend.getAttType().toString());
            Date scanDate = attend.getScanDate();
            Date scanTime = attend.getScanTime();
            Time time = new DateUtils().newTime(scanTime);
            scanDate.setTime(time.getTime());
            history.setTimestamp(scanDate.getTime());
            attendanceHistories.add(history);
        });

        return attendanceHistories;
    }


    public Long findAbsenceDays(Long employeeId) {
        return attendanceService.findAbsenceDays(employeeId);
    }
}
