/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.reader;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import javax.websocket.server.PathParam;

@Controller
public class GreetingController {


    @MessageMapping("/reader/{companyId}")
    public Greeting greeting(HelloMessage message, @PathParam("companyId") Long companyId) throws Exception {
        System.out.println("companyId:" + companyId);
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/")
    public String test() {
        return "test";
    }

}
