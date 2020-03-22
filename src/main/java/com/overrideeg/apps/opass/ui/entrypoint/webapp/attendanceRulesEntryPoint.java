package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.attendanceRules;
import com.overrideeg.apps.opass.service.attendanceRulesService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;


@Component
@RequestMapping(ApiUrls.attendanceRules_EP)
public class attendanceRulesEntryPoint extends RestEntryPoint<attendanceRules> {

    public attendanceRulesEntryPoint(final attendanceRulesService inService) {
        setService(inService);
    }


}