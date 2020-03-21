package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.country;
import com.overrideeg.apps.opass.service.countryService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping(ApiUrls.country_EP)
public class countryEntryPoint extends RestEntryPoint<country> {

    public countryEntryPoint(final countryService inService) {
        setService(inService);
    }

    @Override
    protected country[] entityListToArray(List<country> inEntityList) {
        return inEntityList.toArray(new country[inEntityList.size()]);
    }

}