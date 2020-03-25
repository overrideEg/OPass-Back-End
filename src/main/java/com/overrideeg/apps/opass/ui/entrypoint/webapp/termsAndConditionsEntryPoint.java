package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.appConstants.termsAndConditions;
import com.overrideeg.apps.opass.service.termsAndConditionsService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.termsAndConditions_EP)
public class termsAndConditionsEntryPoint extends RestEntryPoint<termsAndConditions> {

    public termsAndConditionsEntryPoint(final termsAndConditionsService inService) {
        setService(inService);
    }


}