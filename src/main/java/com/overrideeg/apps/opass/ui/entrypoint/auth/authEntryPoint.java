/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.auth;

import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.service.authService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;


@CrossOrigin(origins = "*"
        , methods = {RequestMethod.POST,
        RequestMethod.DELETE, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.HEAD},
        allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping(ApiUrls.Auth_ep)
public class authEntryPoint {

    @Autowired
    authService authenticationService;

    @PostMapping
    public @ResponseBody
    authResponse userLogin(@RequestBody authRequest loginCredentials) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Long companyId;
        try {
            int i = loginCredentials.getUserName().indexOf(".");
            companyId = Long.parseLong(loginCredentials.getUserName().substring(0, i));
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }

        if (companyId == 0) {
            TenantContext.setCurrentTenant(null);
        }
        if (companyId != 0) {
            TenantContext.setCurrentTenant(companyId);
        }

        authResponse returnValue = new authResponse();

        Users authenticatedUser = authenticationService.authenticate(loginCredentials.getUserName(), loginCredentials.getPassword());

        // Reset Access Token
        authenticationService.resetSecurityCridentials(loginCredentials.getPassword(), authenticatedUser, loginCredentials.getMacAddress());

        authResponse companyAndBranch = authenticationService.findCompanyAndBranch(authenticatedUser, returnValue);
        String accessToken = authenticationService.issueAccessToken(authenticatedUser);

        returnValue.setUser(authenticatedUser);
        returnValue.setToken(accessToken);
        returnValue.setDepartment(companyAndBranch.getDepartment());
        returnValue.setBranch(companyAndBranch.getBranch());
        returnValue.setCompany_id(companyId);
        return returnValue;
    }
}
