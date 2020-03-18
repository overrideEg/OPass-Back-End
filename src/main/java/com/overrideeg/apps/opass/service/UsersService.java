/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.exceptions.MissingRequiredFieldException;
import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.io.repositories.UsersRepo;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsersService extends AbstractService<Users> {

    EntityUtils userProfileUtils = new EntityUtils();

    public UsersService(final UsersRepo inRepository) {
        super(inRepository);
    }

    @Override
    public Users save(Users user) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {

        if (user.getEmail().equals(null) || user.getUserName().equals(null)) {
            throw new MissingRequiredFieldException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        List<String> whereNames = new ArrayList<>(Arrays.asList("userName", "email"));
        List whereValues = new ArrayList(Arrays.asList(user.getUserName(), user.getEmail()));

        List<Users> query = createQuery("select u from Users u where " +
                "(u.userName=:userName)and(u.email=:email)", whereNames, whereValues);
        Users existingUser = new Users();
        if (query.size() > 0)
            existingUser = query.get(0);
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