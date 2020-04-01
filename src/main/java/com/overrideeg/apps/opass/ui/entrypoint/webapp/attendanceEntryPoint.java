/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.annotations.Secured;
import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.service.attendanceService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.attendance_EP)
@Secured(methodsToSecure = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET})
public class attendanceEntryPoint extends RestEntryPoint<attendance> {

    public attendanceEntryPoint(final attendanceService inService) {
        setService(inService);
    }


}