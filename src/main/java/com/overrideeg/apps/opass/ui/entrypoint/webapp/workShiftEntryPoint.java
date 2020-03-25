package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.service.workShiftService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(ApiUrls.workShift_EP)
public class workShiftEntryPoint extends RestEntryPoint<workShift> {

    public workShiftEntryPoint(final workShiftService inService) {
        setService(inService);
    }

}