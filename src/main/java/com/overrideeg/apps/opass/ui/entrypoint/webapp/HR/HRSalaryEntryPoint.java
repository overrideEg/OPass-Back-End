/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.webapp.HR;


import com.overrideeg.apps.opass.io.entities.HR.HRSalary;
import com.overrideeg.apps.opass.service.HR.HRSalaryService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping(ApiUrls.HRSalary_EP)
public class HRSalaryEntryPoint extends RestEntryPoint<HRSalary> {

    public HRSalaryEntryPoint ( final HRSalaryService inService ) {
        setService(inService);
    }


}