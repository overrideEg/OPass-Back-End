/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.annotations.Secured;
import com.overrideeg.apps.opass.io.entities.qrMachine;
import com.overrideeg.apps.opass.service.qrMachineService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(ApiUrls.qrMachine_EP)
@Secured(methodsToSecure = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET})
public class qrMachineEntryPoint extends RestEntryPoint<qrMachine> {
    @Autowired
    ResolveTenant resolveTenant;
    @Autowired
    qrMachineService qrMachineService;

    public qrMachineEntryPoint(final qrMachineService inService) {
        setService(inService);
    }

    @GetMapping("/findByDepAndBr")
    public @ResponseBody
    qrMachine qrMachineForDepAndBranch(@RequestParam("departmentId") Long departmentId, @RequestParam("branchId") Long branchId, @RequestHeader Long tenantId, HttpServletRequest request) {
        resolveTenant.resolve(tenantId, request);
        return qrMachineService.findQrMachineForDepartmentAndBranch(departmentId, branchId);
    }

}