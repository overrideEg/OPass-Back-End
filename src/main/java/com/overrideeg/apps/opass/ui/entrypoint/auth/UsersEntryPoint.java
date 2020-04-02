/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.auth;

import com.overrideeg.apps.opass.annotations.Secured;
import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.service.UsersService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(ApiUrls.Users_EP)
@Secured(methodsToSecure = {RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.GET})
public class UsersEntryPoint extends RestEntryPoint<Users> {

    @Autowired
    UsersService usersService;


    public UsersEntryPoint(final UsersService inService) {
        setService(inService);
    }


    @PostMapping("/resetPassword")
    public @ResponseBody
    ResponseModel runReport(@RequestHeader Long tenantId, HttpServletRequest servletRequest, @RequestBody restorePasswordRequest request) throws Exception {
        resolveTenant(tenantId, servletRequest);
        return usersService.resetPassword(request);
    }
}