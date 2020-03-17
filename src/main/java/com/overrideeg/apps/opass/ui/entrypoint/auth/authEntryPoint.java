/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.auth;

import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.service.authService;
import com.overrideeg.apps.opass.system.ApiUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;

@Component
@RequestMapping(ApiUrls.Auth_ep)
public class authEntryPoint {

    @Autowired
    authService authenticationService;


    @PostMapping
    public @ResponseBody
    authResponse userLogin(@RequestBody authRequest loginCredentials) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        authResponse returnValue = new authResponse();

        Users authenticatedUser = authenticationService.authenticate(loginCredentials.getUserName(), loginCredentials.getPassword());

        // Reset Access Token
        authenticationService.resetSecurityCridentials(loginCredentials.getPassword(), authenticatedUser);

        String accessToken = authenticationService.issueAccessToken(authenticatedUser);

        returnValue.setUser(authenticatedUser);
        returnValue.setToken(accessToken);

        return returnValue;
    }
}
