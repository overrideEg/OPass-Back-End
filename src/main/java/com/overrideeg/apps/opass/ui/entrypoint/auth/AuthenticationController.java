/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.auth;


import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.repositories.UserRepo;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.system.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest data) {

        try {
            String username = data.getUsername();
            User user = this.users.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider
                    .createToken(username, user.getRoles());
            checkMacAddress(user, data.getMacAddress());
            user.setToken(token);
            return ok(user);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
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

    public User checkMacAddress(User authenticatedUser, String macAddress) {
        User updatedUser = authenticatedUser;
        String authority = authenticatedUser.getAuthorities().stream().findFirst().get().getAuthority();
        if (authority.equals("user")) {
            if (macAddress == null)
                throw new BadCredentialsException("Mac Address Required");
            if (authenticatedUser.getMacAddress() == null) {
                authenticatedUser.setMacAddress(macAddress);
                updatedUser = this.users.save(authenticatedUser);
            } else if (!authenticatedUser.getMacAddress().equals(macAddress))
                throw new BadCredentialsException("Invalid Mac Address");

        }
        return updatedUser;
    }
}
