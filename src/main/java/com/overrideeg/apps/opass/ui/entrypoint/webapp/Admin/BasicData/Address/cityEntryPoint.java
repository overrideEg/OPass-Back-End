package com.overrideeg.apps.opass.ui.entrypoint.webapp.Admin.BasicData.Address;

import com.overrideeg.apps.opass.io.entity.Admin.BasicData.Address.city;
import com.overrideeg.apps.opass.io.entity.Admin.BasicData.Address.cityLocalized;
import com.overrideeg.apps.opass.service.Admin.BasicData.Address.cityService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping(ApiUrls.city_EP)

public class cityEntryPoint extends RestEntryPoint<city, cityLocalized> {

    public cityEntryPoint(final cityService inService) {
        setService(inService);
    }

    @Override
    protected city[] entityListToArray(List <city> inEntityList) {
        return inEntityList.toArray(new city[inEntityList.size()]);
    }

}