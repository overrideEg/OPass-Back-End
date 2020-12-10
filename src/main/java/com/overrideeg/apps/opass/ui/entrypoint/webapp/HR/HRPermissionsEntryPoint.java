/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.webapp.HR;

import com.overrideeg.apps.opass.io.entities.HR.HRPermissions;
import com.overrideeg.apps.opass.service.HR.HRPermissionsService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping(ApiUrls.HRPermissions_EP)
public class HRPermissionsEntryPoint extends RestEntryPoint<HRPermissions> {

    public HRPermissionsEntryPoint ( final HRPermissionsService inService ) {
        setService(inService);
    }

}