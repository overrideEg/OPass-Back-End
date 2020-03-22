package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.service.employeeService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;


@Component
@RequestMapping(ApiUrls.employee_EP)
public class employeeEntryPoint extends RestEntryPoint<employee> {

    public employeeEntryPoint(final employeeService inService) {
        setService(inService);
    }

    @Override
    public employee postOne(@Valid employee req, Long tenantId, HttpServletRequest request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        return mService.save(req, tenantId);
    }
}