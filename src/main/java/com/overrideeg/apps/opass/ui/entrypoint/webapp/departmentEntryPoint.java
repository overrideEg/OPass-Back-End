package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.department;
import com.overrideeg.apps.opass.service.departmentService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Component
@RequestMapping(ApiUrls.department_EP)
public class departmentEntryPoint extends RestEntryPoint<department> {

    public departmentEntryPoint(final departmentService inService) {
        setService(inService);
    }

    @Override
    protected department[] entityListToArray(List<department> inEntityList) {
        return inEntityList.toArray(new department[inEntityList.size()]);
    }

}