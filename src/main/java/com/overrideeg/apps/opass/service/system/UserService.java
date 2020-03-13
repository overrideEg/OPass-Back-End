package com.overrideeg.apps.opass.service.system;

import com.overrideeg.apps.opass.io.entities.system.Users;
import com.overrideeg.apps.opass.io.repositories.system.UserRepo;
import com.overrideeg.apps.opass.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<Users> {

    public UserService(final UserRepo inRepository) {
        super(inRepository);
    }

}