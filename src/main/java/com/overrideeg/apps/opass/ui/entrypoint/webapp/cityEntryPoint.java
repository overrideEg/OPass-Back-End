/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.city;
import com.overrideeg.apps.opass.service.entityServices.cityService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping(ApiUrls.city_EP)

public class cityEntryPoint extends RestEntryPoint<city> {

    public cityEntryPoint(final cityService inService) {
        setService(inService);
    }

    @Override
    protected city[] entityListToArray(List<city> inEntityList) {
        return inEntityList.toArray(new city[inEntityList.size()]);
    }

}