/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.reader;

import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.valueObjects.AttendanceHistory;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import com.overrideeg.apps.opass.service.attendanceService;
import com.overrideeg.apps.opass.service.readerService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import com.overrideeg.apps.opass.utils.DateUtils;
import com.overrideeg.apps.opass.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*"
        , methods = {RequestMethod.POST,
        RequestMethod.DELETE, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.HEAD},
        allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping(ApiUrls.reader_ep)
public class readerEntryPoint {

    @Autowired
    readerService readerService;

    @Autowired
    attendanceService attendanceService;

    @Autowired
    TenantResolver tenantResolver;

    @Autowired
    ResolveTenant resolveTenant;

    @PostMapping
    public @ResponseBody
    AttendanceHistory readQr(@RequestBody readerRequest request) {
        //initiate tenant DB connection
        handleTenant(request.getCompany_id());
        //create attendance record
        final attendance attendance = readerService.validate(request);
        //save attendance log in DB
        attendanceService.save(attendance);
        //return attendance log in request response
        TenantContext.setCurrentTenant(null);
        company company = tenantResolver.findCompanyForTenantId(request.getCompany_id());
        DateUtils dateUtils = new DateUtils();

        AttendanceHistory history = new AttendanceHistory();
        if (attendance.getWorkShift() != null)
            history.setShiftName(attendance.getWorkShift().getName());
        else
            history.setShiftName(new translatedField("لا يوجد مناوبةـ سجل فقط", "No Shift Log Only", "No Shift"));
        history.setStatus(attendance.getAttStatus().toString());
        history.setType(attendance.getAttType().toString());
        Date scanTime = attendance.getScanTime();
        LocalDateTime date = dateUtils.convertToLocalDateTimeViaInstant(scanTime, company.getTimeZone() != null ? company.getTimeZone() : TimeZone.getDefault());
        Date scanTimeAtZone = dateUtils.convertToDateViaInstant(date, company.getTimeZone() != null ? company.getTimeZone() : TimeZone.getDefault());
        history.setTimestamp(scanTimeAtZone.getTime());
        return history;
    }

    @PostMapping("/adminReading")
    public @ResponseBody
    List<attendance> adminReading ( @RequestBody readerAdminRequest adminRequest, @RequestHeader Long tenantId, HttpServletRequest request ) {
        // resolve tenant
        resolveTenant.resolve(tenantId, request);
        return readerService.adminValidate(adminRequest, tenantId);
    }

    @PostMapping("/adminReading/arr")
    public @ResponseBody
    List<attendance> adminReadingArray ( @RequestBody List<readerAdminRequest> adminRequest, @RequestHeader Long tenantId, HttpServletRequest request ) {
        // resolve tenant
        resolveTenant.resolve(tenantId, request);
        List<attendance> returnValue = new ArrayList<>();
        List<readerAdminRequest> sortedRequest = adminRequest.stream()
                .sorted(Comparator.comparing(readerAdminRequest::getDate)).collect(Collectors.toList());
        sortedRequest.forEach(req -> {
            returnValue.addAll(readerService.adminValidate(req, tenantId));
        });
        return returnValue;
    }


    @PostMapping("/arr")
    public @ResponseBody
    List<AttendanceHistory> readQrArray ( @RequestBody readerArrayRequest request ) {
        // sort by scanTime
        List<readerRequest> sortedRequest = request.getRequest().stream().sorted(Comparator.comparing(readerRequest::getScan_time)).collect(Collectors.toList());
        List<AttendanceHistory> attendanceHistories = new ArrayList<>();
        // invoke request foreach req
        sortedRequest.forEach(req -> {
            try {
                attendanceHistories.add(readQr(req));
            } catch (Exception e) {
                e.printStackTrace();
                new Log(getClass(), e);
            }

        });
        return attendanceHistories;
    }


    /**
     * method that resolve tenant for reader request null and zero are illegal
     *
     * @param tenantId tenant
     */
    private void handleTenant(Long tenantId) {
        if (tenantId == 0 || tenantId == null) {
            throw new AuthenticationException("error This Tenant Are Illegal");
        }
        if (tenantId != 0) {
            TenantContext.setCurrentTenant(tenantId);
        }
    }
}

