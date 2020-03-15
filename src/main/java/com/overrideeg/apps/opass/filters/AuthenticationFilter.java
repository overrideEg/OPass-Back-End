/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.filters;

import com.overrideeg.apps.opass.annotations.Secured;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Secured
public class AuthenticationFilter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest hsr,
                             HttpServletResponse hsr1, Object handler) throws Exception {
        System.out.println("preHandle - " + handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1,
                           Object handler, ModelAndView mav) throws Exception {
        System.out.println("postHandle - " + handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1,
                                Object handler, Exception excptn) throws Exception {
        System.out.println("afterCompletion - " + handler);
    }
}
