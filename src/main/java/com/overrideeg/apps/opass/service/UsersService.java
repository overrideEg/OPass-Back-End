/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.exceptions.MissingRequiredFieldException;
import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.io.repositories.UsersRepo;
import com.overrideeg.apps.opass.ui.entrypoint.auth.restorePasswordRequest;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
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
                "(u.userName=:userName)or(u.email=:email)", whereNames, whereValues);
        Users existingUser = new Users();
        if (query.size() > 0)
            existingUser = query.get(0);
        if (existingUser.getId() != null) {
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
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

    @Override
    public ResponseModel update(Users inEntity) throws NoSuchMethodException {
        Users user = find(inEntity.getId()).get();
        inEntity.setEncryptedPassword(user.getEncryptedPassword());
        inEntity.setSalt(user.getSalt());
        inEntity.setToken(user.getToken());
        return super.update(inEntity);
    }

    public ResponseModel resetPassword(restorePasswordRequest restoreRequest) throws NoSuchMethodException {
        Users existsUser = find("userName", restoreRequest.getUserName());

        if (existsUser.getId().equals(null))
            throw new NoRecordFoundException("This user not registered");

        try {
            String encryptedOldPassword = userProfileUtils.generateSecurePassword(restoreRequest.getOldPassword(), existsUser.getSalt());
            if (!encryptedOldPassword.equalsIgnoreCase(existsUser.getEncryptedPassword()))
                throw new AuthenticationException("Password Entered Not True");
        } catch (Exception e) {
            throw new AuthenticationException("Password Entered Not True");
        }

        // Generate secure password
        String encryptedPassword = userProfileUtils.generateSecurePassword(restoreRequest.getNewPassword(), existsUser.getSalt());
        existsUser.setEncryptedPassword(encryptedPassword);

        return super.update(existsUser);
    }
}