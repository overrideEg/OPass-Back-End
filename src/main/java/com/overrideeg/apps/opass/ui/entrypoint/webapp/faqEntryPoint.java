package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.appConstants.faq;
import com.overrideeg.apps.opass.service.faqService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping(ApiUrls.faq_EP)
public class faqEntryPoint extends RestEntryPoint<faq> {

    public faqEntryPoint(final faqService inService) {
        setService(inService);
    }


}