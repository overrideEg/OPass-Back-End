package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.service.companyService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.company_EP)
public class companyEntryPoint extends RestEntryPoint<company> {

    public companyEntryPoint(final companyService inService) {
        setService(inService);
    }


}