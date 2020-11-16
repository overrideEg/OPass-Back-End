/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.webapp.HR;


import com.overrideeg.apps.opass.io.entities.HR.HRSalaryCalculationDocument;
import com.overrideeg.apps.opass.service.HR.HRSalaryCalculationDocumentService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping(ApiUrls.HRSalaryCalculationDocument_EP)
public class HRSalaryCalculationDocumentEntryPoint extends RestEntryPoint<HRSalaryCalculationDocument> {

    public HRSalaryCalculationDocumentEntryPoint ( final HRSalaryCalculationDocumentService inService ) {
        setService(inService);
    }


}