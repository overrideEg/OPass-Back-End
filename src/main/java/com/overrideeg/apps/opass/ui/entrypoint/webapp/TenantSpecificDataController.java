/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.TenantSpecificData;
import com.overrideeg.apps.opass.io.repositories.TenantSpecificDataRepository;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TenantSpecificDataController {

    @Autowired
    private TenantSpecificDataRepository tenantSpecificDataRepository;

    @Autowired
    private TenantResolver tenantResolver;


    /**
     * Push data to specific tenant db based on tenantId passed as path variable
     *
     * @param tenantId
     * @param username
     * @param tenantSpecificString
     * @return
     */
    @PostMapping("/api/create/data/{tenantId}/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public TenantSpecificData createTenantSpecificData(@PathVariable Long tenantId, @PathVariable String username,
                                                       @RequestBody String tenantSpecificString) {
        TenantContext.setCurrentTenant(tenantId);
        TenantSpecificData tenantSpecificData = new TenantSpecificData();
        tenantSpecificData.setCurrentTenantId(tenantId);
        tenantSpecificData.setUsername(username);
        tenantSpecificData.setSampleData(tenantSpecificString);

        return tenantSpecificDataRepository.save(tenantSpecificData);
    }

}