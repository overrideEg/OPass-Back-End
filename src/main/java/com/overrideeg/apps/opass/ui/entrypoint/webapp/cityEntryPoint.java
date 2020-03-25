package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.city;
import com.overrideeg.apps.opass.service.cityService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.city_EP)
public class cityEntryPoint extends RestEntryPoint<city> {

    public cityEntryPoint(final cityService inService) {
        setService(inService);
    }


}