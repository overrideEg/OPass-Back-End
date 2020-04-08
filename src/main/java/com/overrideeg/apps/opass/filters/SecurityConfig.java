/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.filters;


import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.security.jwt.JwtSecurityConfigurer;
import com.overrideeg.apps.opass.system.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and().authorizeRequests()
                .antMatchers(ApiUrls.Auth_ep + "/login").permitAll()
                .antMatchers(ApiUrls.Auth_ep + "/logout").authenticated()
                .antMatchers(HttpMethod.POST, ApiUrls.Users_EP + "/**").anonymous()
                .antMatchers("/v3/api-docs/**").anonymous()
                .antMatchers(ApiUrls.reader_ep + "/**").anonymous()
                .antMatchers(ApiUrls.subscriptionPlan_EP + "/**").authenticated()
                .antMatchers(ApiUrls.subscription_EP + "/**").authenticated()
                .antMatchers(HttpMethod.POST, ApiUrls.reportDefinition_EP + "/**").hasRole("systemAdmin")
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**/**", configuration);
        return source;
    }

}

