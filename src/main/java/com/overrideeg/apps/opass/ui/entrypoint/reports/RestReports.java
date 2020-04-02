/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.reports;

import com.overrideeg.apps.opass.annotations.Secured;
import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.service.RestReportsService;
import com.overrideeg.apps.opass.service.system.RestLogService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(ApiUrls.REST_REPORT_ep)
@Secured(methodsToSecure = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET})
public class RestReports {


    @Autowired
    RestReportsService restReportsService;
    @Autowired
    RestLogService restLogService;

    @GetMapping("attendanceHistory/{employeeId}")
    public @ResponseBody
    List<attendance> findAttendanceHistory(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestHeader Long tenantId, HttpServletRequest hsr, @PathVariable Long employeeId) {

        resolveTenant(tenantId, hsr);

        return restReportsService.createAttendanceHistoryReport(employeeId, page, pageSize);
    }

    @GetMapping("absenceDays/{employeeId}")
    public @ResponseBody
    Long findAbsenceDays(
            @RequestHeader Long tenantId, HttpServletRequest hsr, @PathVariable Long employeeId) {
        resolveTenant(tenantId, hsr);
        return restReportsService.findAbsenceDays(employeeId);
    }


    public void resolveTenant(Long tenantId, HttpServletRequest request) {
        if (tenantId == 0) {
            TenantContext.setCurrentTenant(null);
        }
        if (tenantId != 0) {
            TenantContext.setCurrentTenant(tenantId);
        }
        restLogService.saveLog(request.getRequestURI(), request.getRemoteAddr(), request.getMethod());
    }

}
