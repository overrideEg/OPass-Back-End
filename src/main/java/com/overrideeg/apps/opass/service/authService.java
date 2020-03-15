/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class authService {
    @Autowired
    UsersService usersService;

//    DAO database;

    public Users authenticate(String userName, String password) throws AuthenticationException {

        Users storedUser = usersService.find("userName", userName); // User name must be unique in our system

        if (storedUser.getId() == null) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }


        String encryptedPassword = null;

        encryptedPassword = new EntityUtils().generateSecurePassword(password, storedUser.getSalt());

        boolean authenticated = false;
        if (encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword())) {
            if (userName != null && userName.equalsIgnoreCase(storedUser.getUserName())) {
                authenticated = true;
            }
        }

        if (!authenticated) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }

        return storedUser;

    }

    public String issueAccessToken(Users userProfile) throws AuthenticationException {
        String returnValue = null;

        String newSaltAsPostfix = userProfile.getSalt();
        String accessTokenMaterial = userProfile.getUserId() + newSaltAsPostfix;

        byte[] encryptedAccessToken = null;
        try {
            encryptedAccessToken = new EntityUtils().encrypt(userProfile.getEncryptedPassword(), accessTokenMaterial);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Faled to issue secure access token");
        }

        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

        // Split token into equal parts
        int tokenLength = encryptedAccessTokenBase64Encoded.length();

        String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

        userProfile.setToken(tokenToSaveToDatabase);
        updateUserProfile(userProfile);

        return returnValue;
    }

    private void updateUserProfile(Users userProfile) {

        try {
            this.usersService.update(userProfile);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void resetSecurityCridentials(String password, Users userProfile) {
        // Gerenerate a new salt
        EntityUtils userUtils = new EntityUtils();
        String salt = userUtils.getSalt(30);

        // Generate a new password
        String securePassword = userUtils.generateSecurePassword(password, salt);
        userProfile.setSalt(salt);
        userProfile.setEncryptedPassword(securePassword);

        // Update user profile
        updateUserProfile(userProfile);

    }

}
