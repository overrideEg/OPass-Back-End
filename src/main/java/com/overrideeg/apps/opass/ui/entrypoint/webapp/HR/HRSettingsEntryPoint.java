/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.webapp.HR;


import com.overrideeg.apps.opass.io.entities.HR.HRSettings;
import com.overrideeg.apps.opass.service.HR.HRSettingsService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping(ApiUrls.HRSettings_EP)
public class HRSettingsEntryPoint extends RestEntryPoint<HRSettings> {

    public HRSettingsEntryPoint ( final HRSettingsService inService ) {
        setService(inService);
    }


}