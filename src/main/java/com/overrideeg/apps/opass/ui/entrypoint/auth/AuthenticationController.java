/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.auth;


import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.repositories.UserRepo;
import com.overrideeg.apps.opass.service.UserService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import com.overrideeg.apps.opass.system.security.jwt.JwtTokenProvider;
import com.overrideeg.apps.opass.ui.entrypoint.auth.model.AuthenticationRequest;
import com.overrideeg.apps.opass.ui.entrypoint.auth.model.changeCredResponse;
import com.overrideeg.apps.opass.ui.entrypoint.auth.model.changePassword;
import com.overrideeg.apps.opass.ui.entrypoint.auth.model.changeUserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(ApiUrls.Auth_ep)
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepo users;

    @Autowired
    ResolveTenant resolveTenant;

    @Autowired
    UserService userService;

    @Autowired
    TenantResolver tenantResolver;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest data) {

        try {
            String username = data.getUsername();
            TenantContext.setCurrentTenant(null);
            User user = this.tenantResolver.findUserFromMasterDatabaseByUserName(username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            checkMacAddress(user, data.getMacAddress());
            String token = jwtTokenProvider
                    .createToken(username, user.getRoles());
            user.setToken(token);
            return ok(user);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid username/password supplied");
        }
    }

    @PutMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody changePassword data) {

        try {
            String username = data.getUsername();
            TenantContext.setCurrentTenant(null);
            this.tenantResolver.findUserFromMasterDatabaseByUserName(username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getOldPassword()));
            String encodedNewPassword = this.passwordEncoder.encode(data.getNewPassword());
            this.tenantResolver.updatePassword(username, encodedNewPassword);

            changeCredResponse credResponse = new changeCredResponse();
            credResponse.setCredentialType("changePassword");
            credResponse.setUpdated(true);
            credResponse.setUserName(username);

            return ok(credResponse);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid username/password supplied");
        }
    }

    @PutMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestBody changePassword data) {
        try {
            String username = data.getUsername();
            TenantContext.setCurrentTenant(null);
            this.tenantResolver.findUserFromMasterDatabaseByUserName(username);
            String encodedNewPassword = this.passwordEncoder.encode(data.getUsername());
            this.tenantResolver.updatePassword(username, encodedNewPassword);
            changeCredResponse credResponse = new changeCredResponse();
            credResponse.setCredentialType("resetPassword");
            credResponse.setUpdated(true);
            credResponse.setUserName(username);
            return ok(credResponse);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid username/password supplied");
        }
    }

    @PutMapping("/changeUserName")
    public ResponseEntity changeUserName(@RequestBody changeUserName data) {

        try {
            String username = data.getOldUserName();
            TenantContext.setCurrentTenant(null);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            User user = this.tenantResolver.findUserFromMasterDatabaseByUserName(data.getNewUserName());
            if (user.getId() != null)
                throw new BadCredentialsException("supplied username are exists please try another one");
            else
                this.tenantResolver.updateUserName(data.getOldUserName(), data.getNewUserName());
            changeCredResponse credResponse = new changeCredResponse();
            credResponse.setCredentialType("changeUserName");
            credResponse.setUpdated(true);
            credResponse.setUserName(username);
            return ok(credResponse);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid username/password supplied");
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "Logged Out";

    }

    //todo check
    public User checkMacAddress(User authenticatedUser, String macAddress) {
        User updatedUser = authenticatedUser;
        Optional<? extends GrantedAuthority> user = authenticatedUser.getAuthorities().stream().filter(o -> o.getAuthority().equals("user")).findFirst();
        if (user.isPresent()) {
            if (macAddress == null)
                throw new BadCredentialsException("Mac Address Required");
            if (authenticatedUser.getMacAddress() == null) {
                authenticatedUser.setMacAddress(macAddress);
                updatedUser = this.userService.update(authenticatedUser.getId(), authenticatedUser.getMacAddress());
            } else if (!authenticatedUser.getMacAddress().equals(macAddress))
                throw new BadCredentialsException("Invalid Mac Address");

        }
        return updatedUser;
    }
}
