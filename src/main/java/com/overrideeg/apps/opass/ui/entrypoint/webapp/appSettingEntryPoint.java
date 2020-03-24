package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.appConstants.appSetting;
import com.overrideeg.apps.opass.service.appSettingService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping(ApiUrls.appSetting_EP)
public class appSettingEntryPoint extends RestEntryPoint<appSetting> {

    public appSettingEntryPoint(final appSettingService inService) {
        setService(inService);
    }


}