package com.overrideeg.apps.opass.ui.entrypoint.webapp.system;

import com.overrideeg.apps.opass.io.entities.system.RestLog;
import com.overrideeg.apps.opass.service.system.RestLogService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping(ApiUrls.RestLog_EP)
public class RestLogEntryPoint extends RestEntryPoint<RestLog> {

    public RestLogEntryPoint(final RestLogService inService) {
        setService(inService);
    }

    @Override
    protected RestLog[] entityListToArray(List<RestLog> inEntityList) {
        return inEntityList.toArray(new RestLog[inEntityList.size()]);
    }

}