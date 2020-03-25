package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.qrMachine;
import com.overrideeg.apps.opass.service.qrMachineService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.qrMachine_EP)
public class qrMachineEntryPoint extends RestEntryPoint<qrMachine> {

    public qrMachineEntryPoint(final qrMachineService inService) {
        setService(inService);
    }


}