/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.auth;


import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.io.entities.HR.HRSettings;
import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.UserRepo;
import com.overrideeg.apps.opass.service.HR.HRSettingsService;
import com.overrideeg.apps.opass.service.UserService;
import com.overrideeg.apps.opass.service.employeeService;
import com.overrideeg.apps.opass.system.ApiUrls;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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

    @Autowired
    HRSettingsService settingsService;

    EntityUtils entityUtils = new EntityUtils();



    @PostMapping("/login")
    public ResponseEntity<User> login ( @RequestBody AuthenticationRequest data ) {

        try {
            String username = data.getUsername();
            AtomicReference<Long> companyId = new AtomicReference<>(0L);
            Thread th = new Thread(() -> {
                TenantContext.setCurrentTenant(null);
                companyId.set(this.tenantResolver.findCompanyIdForUser(username));
            });
            th.start();th.join();
            this.resolveTenant.resolve(companyId.get(), null);
            User user = this.userService.findByUserName(data.getUsername());
            user = updateFCMToken(user, data.getFcmToken());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            checkAndUpdateMacAddress(user,data.getMacAddress());
            String token = jwtTokenProvider.createToken(username, user.getRoles());
            user.setToken(token);
            this.resolveTenant.resolve(companyId.get(), null);
            HRSettings hrSettings = settingsService.findAll().stream().findFirst().orElse(new HRSettings());
            user.settings = hrSettings;
            return ok(user);
        } catch (Throwable e) {
            throw new AuthenticationException("Invalid username/password supplied");
        }
    }

    private User updateFCMToken ( User authenticatedUser, String fcmToken ) {
        User updatedUser = authenticatedUser;
        {
            if (authenticatedUser.getFcmToken() != null) {
                authenticatedUser.setFcmToken(fcmToken);
                updatedUser = this.userService.update(authenticatedUser);
            }
            return updatedUser;
        }
    }


    @PutMapping("/changePassword")
    public ResponseEntity<changeCredResponse> changePassword ( @RequestBody changePassword data ) {

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


        } catch (Throwable e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<employee> updateProfile ( @RequestBody updateUserData data, @RequestHeader Long tenantId, HttpServletRequest request ) throws Throwable {
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
    public ResponseEntity<changeCredResponse> resetPassword ( @RequestBody changePassword data ) {
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
        } catch (Throwable e) {
            throw new AuthenticationException("Invalid username/password supplied");
        }


    }


    @PutMapping("/changeFcmToken/{username}")
    public ResponseEntity<User> updateFcmToken ( @PathVariable String username, @RequestBody FcmRequest request ) throws Throwable {
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

    @Transactional
    public User checkAndUpdateMacAddress(User authenticatedUser, String macAddress) {
        User updatedUser = authenticatedUser;
        String authority = authenticatedUser.getAuthorities().stream().findFirst().get().getAuthority();
        if (authority.equals("user")) {
            if (macAddress == null)
                throw new BadCredentialsException("Mac Address Required");
            if (authenticatedUser.getMacAddress() == null) {
                authenticatedUser.setMacAddress(macAddress);
                updatedUser = this.userService.update(authenticatedUser);
            } else if (!authenticatedUser.getMacAddress().equals(macAddress))
                throw new BadCredentialsException("Invalid Mac Address");
        }
        return updatedUser;
    }
}
