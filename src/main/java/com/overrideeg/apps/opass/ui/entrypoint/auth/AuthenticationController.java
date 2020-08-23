/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.auth;


import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.UserRepo;
import com.overrideeg.apps.opass.service.UserService;
import com.overrideeg.apps.opass.service.employeeService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Caching.OCacheManager;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import com.overrideeg.apps.opass.system.Mail.EmailService;
import com.overrideeg.apps.opass.system.security.jwt.JwtTokenProvider;
import com.overrideeg.apps.opass.ui.entrypoint.auth.model.*;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Autowired
    employeeService employeeService;

    @Autowired
    EmailService emailService;

    EntityUtils entityUtils = new EntityUtils();


    OCacheManager cacheManager = new OCacheManager();


    @PostMapping("/login")
    public ResponseEntity login ( @RequestBody AuthenticationRequest data ) {

        try {
            String username = data.getUsername();
            TenantContext.setCurrentTenant(null);
            Long companyId = this.tenantResolver.findCompanyIdForUser(username);
            this.resolveTenant.resolve(companyId, null);
            User user = this.userService.findByUserName(data.getUsername());
            user = updateFCMToken(user, data.getFcmToken());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, user.getRoles());
            user.setToken(token);
            return ok(user);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid username/password supplied");
        }
    }

    @GetMapping("/register/{userId}/{employeeId}/{companyId}")
    public ResponseEntity register ( @PathVariable Long userId, @PathVariable Long employeeId, @PathVariable Long companyId ) {
        try {
            this.resolveTenant.resolve(companyId, null);
            User user = this.userService.findById(userId);

//
            String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
            user.setToken(token);


            return ok(user);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid credentials");
        }
    }

    private User updateFCMToken ( User authenticatedUser, String fcmToken ) {
        User updatedUser = authenticatedUser;
        {
            if (authenticatedUser.getFcmToken() != null) {
                authenticatedUser.setFcmToken(fcmToken);
                updatedUser = this.userService.update(authenticatedUser.getId(), authenticatedUser.getFcmToken());
            }
            return updatedUser;
        }
    }


    @PutMapping("/changePassword")
    public ResponseEntity changePassword ( @RequestBody changePassword data ) {

        try {
            String username = data.getUsername();
            TenantContext.setCurrentTenant(null);
            Long companyId = this.tenantResolver.findCompanyIdForUser(username);
            this.resolveTenant.resolve(companyId, null);
            User byUserName = this.userService.findByUserName(username);

            if (!byUserName.getTemporaryCode().equals(data.getTempCode())) {
                throw new AuthenticationException("Temp Code is invalid");
            } else {
                String encodedNewPassword = this.passwordEncoder.encode(data.getNewPassword());
                byUserName.setPassword(encodedNewPassword);
                byUserName.setTemporaryCode(null);
                this.userService.update(byUserName);
                changeCredResponse credResponse = new changeCredResponse();
                credResponse.setCredentialType("changePassword");
                credResponse.setUpdated(true);
                credResponse.setUserName(username);

                return ok(credResponse);
            }


        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    @PostMapping("/updateProfile")
    public ResponseEntity updateProfile ( @RequestBody updateUserData data, @RequestHeader Long tenantId, HttpServletRequest request ) {
        try {
            this.resolveTenant.resolve(0L, null);
            this.resolveTenant.resolve(tenantId, null);
            User user = this.userService.findByUserName(data.getEmail());
            String newPass = this.passwordEncoder.encode(data.getNewPassword());
            user.setPassword(newPass);
            this.userService.update(user);
            employee employee = new employee();
            this.resolveTenant.resolve(tenantId, request);
            Optional<employee> employeeOptional = this.employeeService.find(user.getEmployee().getId());
            if (employeeOptional.isPresent()) {
                employee = employeeOptional.get();
                this.employeeService.update(employee);
            }

            return ok(employee);
        } catch (Exception e) {
            throw new AuthenticationException("Could Not update ");

        }


    }


    @PutMapping("/resetPassword")
    public ResponseEntity resetPassword ( @RequestBody changePassword data ) {
        try {
            String username = data.getUsername();

            TenantContext.setCurrentTenant(null);
            Long companyId = this.tenantResolver.findCompanyIdForUser(username);
            this.resolveTenant.resolve(companyId, null);

            User byUserName = this.userService.findByUserName(data.getUsername());
            String tempCode = entityUtils.generateEmailverificationToken(8);
            byUserName.setTemporaryCode(tempCode);
            this.userService.update(byUserName);
            this.emailService.resetPassword(byUserName, tempCode);

            changeCredResponse credResponse = new changeCredResponse();
            credResponse.setCredentialType("resetPassword");
            credResponse.setUpdated(true);
            credResponse.setUserName(username);
            return ok(credResponse);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid username/password supplied");
        }


    }


    @PutMapping("/changeFcmToken/{username}")
    public ResponseEntity updateFcmToken ( @PathVariable String username, @RequestBody FcmRequest request ) {
        TenantContext.setCurrentTenant(null);
        Long companyId = this.tenantResolver.findCompanyIdForUser(username);
        this.resolveTenant.resolve(companyId, null);

        User byUserName = this.userService.findByUserName(username);
        byUserName.setFcmToken(request.getFcmToken());
        this.userService.update(byUserName);
        return ok(byUserName);
    }

    @GetMapping("/logout")
    public String logout ( HttpServletRequest request, HttpServletResponse response ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "Logged Out";

    }
}
