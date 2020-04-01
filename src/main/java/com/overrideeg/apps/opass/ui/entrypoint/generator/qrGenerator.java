/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.generator;

import com.overrideeg.apps.opass.annotations.Secured;
import com.overrideeg.apps.opass.io.entities.qrMachine;
import com.overrideeg.apps.opass.service.generatorService;
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
@RequestMapping(ApiUrls.Qr_Generator_ep)
@Secured(methodsToSecure = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET})
public class qrGenerator {
    @Autowired
    generatorService generatorService;

    @PostMapping
    public @ResponseBody
    qrMachine userLogin(@RequestBody generatorRequest request, @RequestHeader Long tenantId) throws NoSuchMethodException {
        TenantContext.setCurrentTenant(tenantId);
        return generatorService.validate(request);
    }
}
