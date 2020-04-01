/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.utils;

import com.overrideeg.apps.opass.annotations.Secured;
import org.reflections.Reflections;

import java.util.Set;

public class AuthenticationUtils {
    public static Set<Class<?>> getSecuredEntryPoints() {
        Reflections reflections = new Reflections("com.overrideeg.apps.opass.ui.entrypoint");
        return reflections.getTypesAnnotatedWith(Secured.class);
    }
}
