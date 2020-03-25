package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.country;
import com.overrideeg.apps.opass.service.countryService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.country_EP)
public class countryEntryPoint extends RestEntryPoint<country> {

    public countryEntryPoint(final countryService inService) {
        setService(inService);
    }


}