package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.valueObjects.attendanceRules;
import com.overrideeg.apps.opass.service.attendanceRulesService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(ApiUrls.attendanceRules_EP)
public class attendanceRulesEntryPoint extends RestEntryPoint<attendanceRules> {

    public attendanceRulesEntryPoint(final attendanceRulesService inService) {
        setService(inService);
    }


}