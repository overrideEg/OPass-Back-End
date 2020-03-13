package com.overrideeg.apps.opass.ui.entrypoint.webapp.system;

import com.overrideeg.apps.opass.io.entities.system.Users;
import com.overrideeg.apps.opass.service.system.UserService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Component
@RequestMapping(ApiUrls.User_EP)
public class UserEntryPoint extends RestEntryPoint<Users> {

    public UserEntryPoint(final UserService inService) {
        setService(inService);
    }

    @Override
    protected Users[] entityListToArray(List<Users> inEntityList) {
        return inEntityList.toArray(new Users[inEntityList.size()]);
    }

}