/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.ui.entrypoint.reports;

import com.overrideeg.apps.opass.io.entities.reports.reportDefinition;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import com.overrideeg.apps.opass.service.ReportDefinitionService;
import com.overrideeg.apps.opass.service.reportCategoryService;
import com.overrideeg.apps.opass.service.system.RestLogService;
import com.overrideeg.apps.opass.system.ApiUrls;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@CrossOrigin(origins = "*"
        , methods = {RequestMethod.POST,
        RequestMethod.DELETE, RequestMethod.GET,
        RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.HEAD},
        allowCredentials = "true", allowedHeaders = "*")
@RequestMapping(ApiUrls.reportDefinition_EP)
public class ReportDefinitionEntryPoint {

    @Autowired
    ReportDefinitionService reportDefinitionService;
    @Autowired
    RestLogService restLogService;
    @Autowired
    reportCategoryService categoryService;

    @PostMapping
    public @ResponseBody
    reportDefinition uploadFile(@RequestParam("file") MultipartFile file,
                                @RequestParam("name_ar") String name_ar,
                                @RequestParam("name_en") String name_en,
                                @RequestParam("name_tr") String name_tr,
                                @RequestParam("categoryId") Long categoryId) {
        TenantContext.setCurrentTenant(null);
        reportDefinition reportDefinition = new reportDefinition();
        reportDefinition.setName(new translatedField(name_ar, name_en, name_tr));
        reportDefinition.setReportCategory(categoryService.find(categoryId).get());
        return reportDefinitionService.save(file, reportDefinition);
    }


    @GetMapping
    public @ResponseBody
    List<reportDefinition> getAll(HttpServletRequest request, @RequestHeader Long tenantId,
                                  @RequestParam(name = "start", required = false) Integer start,
                                  @RequestParam(name = "limit", required = false) Integer limit) {
        resolveTenant(tenantId, request);
        if (start == null) start = 0;
        if (limit == null) limit = 25;
        List<reportDefinition> resp = reportDefinitionService.findAll(start, limit);
        return resp;
    }

    private void resolveTenant(Long tenantId, HttpServletRequest request) {
        TenantContext.setCurrentTenant(null);
        restLogService.saveLog(request.getRequestURI(), request.getRemoteAddr(), request.getMethod());
    }


    @PostMapping(value = "/{reportId}", produces = "text/html")
    public @ResponseBody
    HttpServletResponse runReport(@PathVariable(value = "reportId") Long reportId, @RequestHeader Long tenantId, HttpServletResponse response, @RequestBody reportParams params) throws Exception {
        HttpServletResponse res = reportDefinitionService.generateReport(response, reportId, tenantId, params.getParams());
        return res;
    }

    @GetMapping("/getReport/{reportId}")
    public @ResponseBody
    reportDefinition getReport(@PathVariable(value = "reportId") Long reportId, @RequestHeader Long tenantId, HttpServletRequest request) {
        resolveTenant(tenantId, request);
        return reportDefinitionService.find(reportId).get();
    }


    @PutMapping
    public @ResponseBody
    ResponseModel update(@RequestParam("file") MultipartFile file, @RequestParam("reportId") Long reportId, @RequestParam String name_ar, @RequestParam String name_en, @RequestParam String name_tr) throws NoSuchMethodException {
        TenantContext.setCurrentTenant(null);
        reportDefinition reportDefinition = new reportDefinition();
        reportDefinition.setName(new translatedField(name_ar, name_en, name_tr));
        return reportDefinitionService.update(file, reportDefinition, reportId);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseModel deleteEntityById(@PathVariable(value = "id") Long inEntityId, @RequestHeader Long tenantId, HttpServletRequest request) {
        resolveTenant(tenantId, request);
        ResponseModel delete = reportDefinitionService.delete(inEntityId);
        return delete;
    }


}