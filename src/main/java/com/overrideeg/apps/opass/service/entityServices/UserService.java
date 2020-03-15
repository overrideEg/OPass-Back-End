/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service.entityServices;

import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.io.repositories.UsersRepo;
import com.overrideeg.apps.opass.service.AbstractService;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class UserService extends AbstractService<Users> {

    EntityUtils userProfileUtils = new EntityUtils();

    public UserService(final UsersRepo inRepository) {
        super(inRepository);
    }

    @Override
    public Users save(Users user) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {


        Users existingUser = find("userName", user.getUserName());

        if (existingUser.getId() != null) {
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
        }

        // Generate secure public user id
        String userId = userProfileUtils.generateEntityId(30);
        user.setUserId(userId);


        // Generate salt
        String salt = userProfileUtils.getSalt(30);

        user.setSalt(salt);
        // Generate secure password
        String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setEncryptedPassword(encryptedPassword);

        // Record data into a database
        Users returnValue = super.save(user);

        return returnValue;
    }
}