package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.reports.reportCategory;
import com.overrideeg.apps.opass.service.reportCategoryService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.reportCategory_EP)
public class reportCategoryEntryPoint extends RestEntryPoint<reportCategory> {

    public reportCategoryEntryPoint(final reportCategoryService inService) {
        setService(inService);
    }


}