package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.service.employeeService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Component
@RequestMapping(ApiUrls.employee_EP)
public class employeeEntryPoint extends RestEntryPoint<employee> {

    public employeeEntryPoint(final employeeService inService) {
        setService(inService);
    }

    @Override
    protected employee[] entityListToArray(List<employee> inEntityList) {
        return inEntityList.toArray(new employee[inEntityList.size()]);
    }

}