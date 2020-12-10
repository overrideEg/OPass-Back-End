/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.exceptions.FileStorageException;
import com.overrideeg.apps.opass.io.entities.reports.reportDefinition;
import com.overrideeg.apps.opass.io.repositories.ReportDefinitionRepo;
import com.overrideeg.apps.opass.service.files.FileStorageService;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import com.overrideeg.apps.opass.utils.FileStorageProperties;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportDefinitionService extends AbstractService<reportDefinition> {

    @Autowired
    TenantResolver tenantResolver;
    @Autowired
    private FileStorageService fileStorageService;


    public ReportDefinitionService(final ReportDefinitionRepo inRepository) {
        super(inRepository);
    }

    public reportDefinition save(MultipartFile file, reportDefinition reportDefinition) {
        String fileName = null;
        String jasperPath;
        Map<String, String> dbPararms = new HashMap<>();

        try {
            fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);
            String reportPath = new StringBuilder().append(FileStorageProperties.getUploadDir()).append("/").append(fileName).toString();
            jasperPath = new StringBuilder(reportPath.substring(0, reportPath.length() - 5)).append("jasper").toString();

            JasperCompileManager.compileReportToFile(
                    resource.getFile().getPath(), // the path to the jrxml file to compile
                    jasperPath); // the path and name we want to save the compiled file to
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileStorageService.loadFileAsResource(jasperPath).getFile());
            JRParameter[] jsParams = jasperReport.getParameters();

            for (JRParameter param : jsParams) {
                if (!param.isSystemDefined() && param.isForPrompting()) {
                    String paramName = param.getName();
                    String paramType = param.getValueClassName();
                    dbPararms.put(paramName, paramType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException(e.getMessage());
        }

        String fileNameJasper = new StringBuilder(file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 5)).append("jasper").toString();
        reportDefinition.setFilePath(jasperPath);
        reportDefinition.setSize(file.getSize());
        reportDefinition.setParams(dbPararms);
        reportDefinition.setFileName(file.getOriginalFilename());
        reportDefinition.setJasperFileName(fileNameJasper);
        return mRepository.save(reportDefinition);
    }


    public ResponseModel update(MultipartFile file, reportDefinition reportDefinition, Long reportId) throws NoSuchMethodException {
        String fileName = null;
        String jasperPath;
        try {
            fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);
            String reportPath = new StringBuilder().append(FileStorageProperties.getUploadDir()).append("/").append(fileName).toString();
            jasperPath = new StringBuilder(reportPath.substring(0, reportPath.length() - 5)).append("jasper").toString();

            JasperCompileManager.compileReportToFile(resource.getFile().getPath(), jasperPath);


        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException(e.getMessage());
        }

        reportDefinition storedReport = find(reportId).get();

        String fileNameJasper = new StringBuilder(file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 5)).append("jasper").toString();
        storedReport.setFilePath(jasperPath);
        storedReport.setSize(file.getSize());
        storedReport.setFileName(file.getOriginalFilename());
        storedReport.setJasperFileName(fileNameJasper);
        storedReport.setName(reportDefinition.getName());
        return update(storedReport);
    }


    public HttpServletResponse generateReport(HttpServletResponse response, Long reportId, Long companyId, Map parameters) throws Exception {
        StringBuilder reportPath = new StringBuilder();
        String uploadDir = FileStorageProperties.getUploadDir();
        reportPath.append(uploadDir).append("/");
        reportDefinition reportDefinition = null;
        try {
            reportDefinition = mRepository.findById(reportId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> params = reportDefinition.getParams();
        parameters.forEach((key, value) -> {
            String parameterType = params.get(key);
            if (parameterType.equalsIgnoreCase("java.util.Date")) {
                String date = (String) parameters.get(key);
                Date dateToRun = null;
                try {
                    dateToRun = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                parameters.put(key, dateToRun);

            }
        });

        reportPath.append(reportDefinition.getFileName());

        Resource resource = fileStorageService.loadFileAsResource(reportDefinition.getFileName());

        System.out.println(reportPath.toString());

        return report(response, resource.getFile().getPath(), companyId, parameters);
    }

    public HttpServletResponse report(HttpServletResponse response, String resource, Long companyId, Map parameters) throws Exception {

        File file = fileStorageService.loadFileAsResource(resource).getFile();
        response.setContentType("text/html");
        String report = resource;

        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();

        JasperDesign jasDesign = JRXmlLoader.load(file);
        JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);

        JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, parameters, getConnection(companyId));

        jasperPrintList.add(jasPrint);
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        exporter.exportReport();
        response.setStatus(HttpServletResponse.SC_OK);

        return response;
    }


    private Connection getConnection(Long companyId) throws IOException, ClassNotFoundException, SQLException {
        Properties config = new Properties();
        InputStream prop = getClass().getClassLoader().getResourceAsStream("application.properties");
        config.load(prop);
        String driverClass = config.getProperty("database.driverClass");
        String host = config.getProperty("database.host");
        String port = config.getProperty("database.port");
        String databaseName = this.tenantResolver.findDataBaseNameByTenantId(companyId);
        if (databaseName == null)
            databaseName = config.getProperty("database.name");
        String username = config.getProperty("database.username");
        String password = config.getProperty("database.password");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "";
        Class.forName(driverClass);
        return DriverManager.getConnection(url, username, password);
    }


}