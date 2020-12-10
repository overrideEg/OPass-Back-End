/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.system.security.jwt;

import com.overrideeg.apps.opass.exceptions.AuthenticationException;
import com.overrideeg.apps.opass.service.CustomUserDetailsService;
import com.overrideeg.apps.opass.service.UserService;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {


    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    ResolveTenant resolveTenant;

    @Autowired
    TenantResolver tenantResolver;

    private String secretKey;

    @PostConstruct
    protected void init () {
        String secret = "com.overrideeg.apps.EWJ.system.security";
        secretKey = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken ( String username, List<String> roles ) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        long monthInMs = 2592000000L;
        jwtProperties.setValidityInMs(monthInMs);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getValidityInMs());

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS512, secretKey)//
                .compact();
    }

    public Authentication getAuthentication(String token)   {
        TenantContext.setCurrentTenant(null);
        Long companyId = this.tenantResolver.findCompanyIdForUser(getUsername(token));
        this.resolveTenant.resolve(companyId, null);
        UserDetails userDetails = this.userService.findByUserName(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthenticationException("Expired or invalid JWT token");
        }
    }

}
