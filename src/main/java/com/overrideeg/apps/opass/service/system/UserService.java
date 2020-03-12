package com.overrideeg.apps.opass.service.system;

import com.overrideeg.apps.opass.io.entity.System.Users;
import com.overrideeg.apps.opass.io.repositories.system.UserRepo;
import com.overrideeg.apps.opass.service.AbstractService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;

@Service
public class UserService extends AbstractService<Users, Null> {

public UserService(final UserRepo inRepository) {
super(inRepository);
}

}