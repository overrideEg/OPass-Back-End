/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.reports;

import com.overrideeg.apps.opass.io.entities.reports.reportDetention;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import com.overrideeg.apps.opass.service.reportDetentionService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(ApiUrls.reportDetention_EP)
public class reportDetentionEntryPoint {

    @Autowired
    reportDetentionService reportDetentionService;


    @PostMapping
    public @ResponseBody
    reportDetention uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String name_ar, @RequestParam String name_en, @RequestParam String name_tr) {
        TenantContext.setCurrentTenant(null);
        reportDetention reportDetention = new reportDetention();
        reportDetention.setName(new translatedField(name_ar, name_en, name_tr));
        return reportDetentionService.save(file, reportDetention);
    }

    @GetMapping
    public @ResponseBody
    HttpServletResponse runReport(@RequestParam Long reportId, @RequestHeader String tenantId, HttpServletResponse response, @RequestBody reportParams params) throws Exception {
        HttpServletResponse respon = reportDetentionService.generateReport(response, reportId, Long.parseLong(tenantId), params.getParams());
        return respon;
    }

    @PutMapping
    public @ResponseBody
    ResponseModel update(@RequestParam("file") MultipartFile file, @RequestParam("reportId") Long reportId, @RequestParam String name_ar, @RequestParam String name_en, @RequestParam String name_tr) throws NoSuchMethodException {
        TenantContext.setCurrentTenant(null);
        reportDetention reportDetention = new reportDetention();
        reportDetention.setName(new translatedField(name_ar, name_en, name_tr));
        return reportDetentionService.update(file, reportDetention, reportId);
    }


}