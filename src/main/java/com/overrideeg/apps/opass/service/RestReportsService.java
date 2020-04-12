/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.valueObjects.AttendanceHistory;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import com.overrideeg.apps.opass.ui.entrypoint.reports.valueObjects.countAttendanceInDate;
import com.overrideeg.apps.opass.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RestReportsService {

    @Autowired
    attendanceService attendanceService;
    @Autowired
    TenantResolver tenantResolver;

    public List<AttendanceHistory> createAttendanceHistoryReport(Long employeeId, Long companyId, Integer page, Integer pageSize) {
        List<attendance> attendanceList = attendanceService.createAttendanceHistoryReport(employeeId, page, pageSize);
        TenantContext.setCurrentTenant(null);
        company company = tenantResolver.findCompanyForTenantId(companyId);
        DateUtils dateUtils = new DateUtils();
        List<AttendanceHistory> attendanceHistories = new ArrayList<>();
        attendanceList.forEach(attend -> {
            AttendanceHistory history = new AttendanceHistory();
            if (attend.getWorkShift() != null)
                history.setShiftName(attend.getWorkShift().getName());
            history.setStatus(attend.getAttStatus().toString());
            history.setType(attend.getAttType().toString());
            Date scanTime = attend.getScanTime();
            LocalDateTime date = dateUtils.convertToLocalDateTimeViaInstant(scanTime, company.getTimeZone());
            Date scanTimeAtZone = dateUtils.convertToDateViaInstant(date, company.getTimeZone());
            history.setTimestamp(scanTimeAtZone.getTime());
            attendanceHistories.add(history);
        });

        return attendanceHistories;
    }


    public Long findAbsenceDays(Long employeeId) {
        return attendanceService.findAbsenceDays(employeeId);
    }

    public List<countAttendanceInDate> findTotalEmployeeAttendanceFromFirstMonth() {
        LocalDate todayDate = LocalDate.now();
        // first day of this month
        Date fromDate = Date.from(todayDate.withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC));
        // last day of this month
        Date toDate = Date.from(todayDate.withDayOfMonth(todayDate.lengthOfMonth()).atStartOfDay().toInstant(ZoneOffset.UTC));
        return attendanceService.findTotalEmployeeAttendanceFromFirstMonth(fromDate, toDate);
    }

}
