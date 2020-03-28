/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.reader;

import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.service.readerService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping
    public @ResponseBody
    attendance readQr(@RequestBody readerRequest request) {
        handleTenant(request.getCompany_id());
        return readerService.validate(request);
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
