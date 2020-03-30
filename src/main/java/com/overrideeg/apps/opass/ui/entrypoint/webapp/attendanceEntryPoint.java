package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.service.attendanceService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.attendance_EP)
public class attendanceEntryPoint extends RestEntryPoint<attendance> {

    public attendanceEntryPoint(final attendanceService inService) {
        setService(inService);
    }


}