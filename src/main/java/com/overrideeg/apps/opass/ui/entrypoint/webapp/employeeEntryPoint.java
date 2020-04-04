/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.annotations.Secured;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.service.employeeService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;


@RestController
@RequestMapping(ApiUrls.employee_EP)
@Secured(methodsToSecure = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET})
public class employeeEntryPoint extends RestEntryPoint<employee> {

    @Autowired
    employeeService employeeService;

    public employeeEntryPoint(final employeeService inService) {
        setService(inService);
    }

    @Override
    public @ResponseBody
    employee postOne(@RequestBody employee req, @RequestHeader Long tenantId, HttpServletRequest request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        validateFields(req);
        resolveTenant(tenantId, request);
        return employeeService.save(req, tenantId);
    }
}