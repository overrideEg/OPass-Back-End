/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.reader;

import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.service.attendanceService;
import com.overrideeg.apps.opass.service.readerService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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


    @PostMapping
    public @ResponseBody
    attendance readQr(@RequestBody readerRequest request) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        //initiate tenant DB connection
        handleTenant(request.getCompany_id());
        //create attendance record
        final attendance attendance = readerService.validate(request);
        //save attendance log in DB
        attendanceService.save(attendance);
        //return attendance log in request response
        return attendance;
    }

    @PostMapping("/arr")
    public @ResponseBody
    List<attendance> readQrArray(@RequestBody List<readerRequest> request) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        // sort by scanTime
        List<readerRequest> sortedRequest = request.stream().sorted(Comparator.comparing(a -> a.getScan_time())).collect(Collectors.toList());
        List<attendance> returnValue = new ArrayList<>();

        // invoke request foreach req
        sortedRequest.forEach(req -> {
            try {
                returnValue.add(readQr(req));
            } catch (Exception e) {
                e.printStackTrace();
                new Log(getClass(), e);
            }
        });
        return returnValue;
    }


    /**
     * method that resolve tenant for reader request null and zero are illegal
     *
     * @param tenantId
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
