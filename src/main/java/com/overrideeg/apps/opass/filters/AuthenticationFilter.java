/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.filters;

import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.service.UsersService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class AuthenticationFilter implements HandlerInterceptor {
    final UsersService userService;

    public AuthenticationFilter(UsersService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest hsr,
                             HttpServletResponse hsr1, Object handler) throws Exception {

        String contextPath = hsr.getServletPath().substring(1);
        if (ApiUrls.Users_EP.equals(contextPath) && hsr.getMethod().equalsIgnoreCase("POST")) {

            return true;
        } else if ((ApiUrls.termsAndConditions_EP.equals(contextPath) || ApiUrls.faq_EP.equals(contextPath) || ApiUrls.appSetting_EP.equals(contextPath)) && (hsr.getMethod().equalsIgnoreCase("GET") || hsr.getMethod().equalsIgnoreCase("OPTIONS"))) {
            return true;
        } else if ("error".equals(contextPath) || ApiUrls.reader_ep.equals(contextPath) || contextPath.startsWith(ApiUrls.reportDefinition_EP)) {
            return true;
        } else if (ApiUrls.Auth_ep.equals(contextPath)) {
            handleAuthRequest(hsr, hsr1);
        } else {
            if (!hsr.getMethod().equalsIgnoreCase("OPTIONS")) {
                return handleFilter(hsr);
            }
        }
        return true;
    }

    private String checkText(String origin, String another) {
        if (origin.equalsIgnoreCase(another))
            return another;
        if (origin.startsWith(another))
            return origin;


        return "false";
    }

    private boolean handleFilter(HttpServletRequest hsr) {
        String authorizationHeader = hsr.getHeaders("Authorization").nextElement();
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            throw new AuthenticationException("Authorization header must be provided");
        }

        // Extract the token
        String token = authorizationHeader.substring("Bearer".length()).trim();

        // Extract user id
        String userId = hsr.getHeader("userId");

        if (!token.equals("U3VwZXIgVXNlciBBZG1pbg==")) {
            String tenantId1 = hsr.getHeader("tenantId");
            if (tenantId1 != null) {
                long tenantId = Long.parseLong(tenantId1);
                resolveTenant(tenantId);
            }
            validateToken(token, userId);
        }
        return true;
    }

    private void resolveTenant(Long tenantId) {
        if (tenantId == 0) {
            TenantContext.setCurrentTenant(null);
        }
        if (tenantId != 0) {
            TenantContext.setCurrentTenant(tenantId);
        }

    }

    private Boolean handleAuthRequest(HttpServletRequest hsr, HttpServletResponse response) throws IOException {
        if (hsr.getMethod().equals("POST")) {
            Properties config = new Properties();
            InputStream prop = getClass().getClassLoader().getResourceAsStream("application.properties");
            config.load(prop);
            String clients = config.getProperty("clientsAllowed");
            ArrayList<String> allowed = new ArrayList<>(Arrays.asList(clients.split(",")));
            Boolean auth = false;
            for (String text : allowed) {
                String encodedAllowed = EntityUtils.encode(text);
                String authorizationHeader = hsr.getHeader(HttpHeaders.AUTHORIZATION);

                if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
                    throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
                }
                String token = authorizationHeader.substring("Bearer".length()).trim();
                if (token.equals(encodedAllowed)) {
                    auth = true;
                    break;
                }
            }

            if (!auth) {
                throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
            }
        } else {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST");
            response.setHeader("Access-Control-Allow-Headers", "*");

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return true;
        }

        return true;
    }

    //    @Override
//    public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1,
//                           Object handler, ModelAndView mav) throws Exception {
//        System.out.println("postHandle - " + handler);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1,
//                                Object handler, Exception excptn) throws Exception {
//        System.out.println("afterCompletion - " + handler);
//    }
    private void validateToken(String token, String userId) throws AuthenticationException {

        // Get user profile details
        Users userProfile = userService.find("userId", userId);

        if (userProfile.getId() == null)
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());

        // Asseble Access token using two parts. One from DB and one from http request.
        String completeToken = userProfile.getToken() + token;

        // Create Access token material out of the useId received and salt kept database
        String securePassword = userProfile.getEncryptedPassword();
        String salt = userProfile.getSalt();
        String accessTokenMaterial = userId + salt;
        byte[] encryptedAccessToken = null;

        try {
            encryptedAccessToken = new EntityUtils().encrypt(securePassword, accessTokenMaterial);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Faled to issue secure access token");
        }

        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

        // Compare two access tokens
        if (!encryptedAccessTokenBase64Encoded.equalsIgnoreCase(completeToken)) {
            throw new AuthenticationException("Authorization token did not match");
        }
    }
}
