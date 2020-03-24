package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.currency;
import com.overrideeg.apps.opass.service.currencyService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping(ApiUrls.currency_EP)
public class currencyEntryPoint extends RestEntryPoint<currency> {

    public currencyEntryPoint(final currencyService inService) {
        setService(inService);
    }


}