package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.service.workShiftService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Component
@RequestMapping(ApiUrls.workShift_EP)
public class workShiftEntryPoint extends RestEntryPoint<workShift> {

    public workShiftEntryPoint(final workShiftService inService) {
        setService(inService);
    }

    @Override
    protected workShift[] entityListToArray(List<workShift> inEntityList) {
        return inEntityList.toArray(new workShift[inEntityList.size()]);
    }

}