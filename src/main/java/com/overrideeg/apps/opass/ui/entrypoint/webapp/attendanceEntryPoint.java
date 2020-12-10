/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.annotations.Secured;
import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.valueObjects.attendanceReport;
import com.overrideeg.apps.opass.service.attendanceService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(ApiUrls.attendance_EP)
@Secured(methodsToSecure = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET})
public class attendanceEntryPoint extends RestEntryPoint<attendance> {

    @Autowired
    ResolveTenant resolveTenant;
    @Autowired
    attendanceService attendanceService;

    public attendanceEntryPoint ( final attendanceService inService ) {
        setService(inService);
    }


    @GetMapping("/dayByDay")
    @ResponseBody
    List<attendanceReport> findAll ( HttpServletRequest request, @RequestHeader Long tenantId,
                                     @RequestParam(name = "start", required = false) Integer start,
                                     @RequestParam(name = "limit", required = false) Integer limit ) {
        resolveTenant.resolve(tenantId, request);
        if (start == null) start = 0;
        if (limit == null) limit = 25;
        return attendanceService.findAttendanceReport(start, limit);
    }

}