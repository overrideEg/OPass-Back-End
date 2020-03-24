package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.subscriptionPlan;
import com.overrideeg.apps.opass.service.subscriptionPlanService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping(ApiUrls.subscriptionPlan_EP)
public class subscriptionPlanEntryPoint extends RestEntryPoint<subscriptionPlan> {

    public subscriptionPlanEntryPoint(final subscriptionPlanService inService) {
        setService(inService);
    }


}