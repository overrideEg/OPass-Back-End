/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.system.Connection;

import com.overrideeg.apps.opass.service.system.RestLogService;
import com.overrideeg.apps.opass.system.Caching.OCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ResolveTenant {
    @Autowired
    RestLogService restLogService;

    public void resolve(Long tenantId, HttpServletRequest request) {

        OCacheManager.getInstance().put("tenantId", tenantId);
        if (TenantContext.getCurrentTenant() != tenantId) {
            if (tenantId == 0) {
                TenantContext.setCurrentTenant(null);
            }
            if (tenantId != 0) {
                TenantContext.setCurrentTenant(tenantId);
            }

        }
        if (request != null)
            restLogService.saveLog(request.getRequestURI(), request.getRemoteAddr(), request.getMethod());
    }
}
