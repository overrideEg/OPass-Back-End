package com.overrideeg.apps.opass.ui.entrypoint.webapp;

import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.service.UsersService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.ui.entrypoint.RestEntryPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(ApiUrls.Users_EP)
public class UsersEntryPoint extends RestEntryPoint<Users> {

    public UsersEntryPoint(final UsersService inService) {
        setService(inService);
    }


}