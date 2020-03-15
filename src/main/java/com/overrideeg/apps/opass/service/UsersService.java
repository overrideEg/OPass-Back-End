package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.io.repositories.UsersRepo;
import org.springframework.stereotype.Service;

@Service
public class UsersService extends AbstractService<Users> {

    public UsersService(final UsersRepo inRepository) {
        super(inRepository);
    }

}