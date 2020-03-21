package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.subscription;
import com.overrideeg.apps.opass.service.subscriptionService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping(ApiUrls.subscription_EP)
public class subscriptionEntryPoint extends RestEntryPoint<subscription> {

    public subscriptionEntryPoint(final subscriptionService inService) {
        setService(inService);
    }

    @Override
    protected subscription[] entityListToArray(List<subscription> inEntityList) {
        return inEntityList.toArray(new subscription[inEntityList.size()]);
    }

}