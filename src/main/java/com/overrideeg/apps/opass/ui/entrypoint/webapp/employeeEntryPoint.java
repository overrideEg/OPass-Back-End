package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.service.employeeService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;


@Component
@RequestMapping(ApiUrls.employee_EP)
public class employeeEntryPoint extends RestEntryPoint<employee> {

    public employeeEntryPoint(final employeeService inService) {
        setService(inService);
    }

    @Override
    public @ResponseBody
    employee postOne(@RequestBody employee req, @RequestHeader Long tenantId, HttpServletRequest request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        return mService.save(req, tenantId);
    }
}