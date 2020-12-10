package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.officialHoliday;
import com.overrideeg.apps.opass.service.officialHolidayService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping(ApiUrls.officialHoliday_EP)
public class officialHolidayEntryPoint extends RestEntryPoint<officialHoliday> {

    public officialHolidayEntryPoint ( final officialHolidayService inService ) {
        setService(inService);
    }


}