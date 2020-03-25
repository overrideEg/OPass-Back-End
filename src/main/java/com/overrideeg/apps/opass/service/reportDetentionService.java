package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.exceptions.FileStorageException;
import com.overrideeg.apps.opass.io.entities.reports.reportDetention;
import com.overrideeg.apps.opass.io.repositories.reportDetentionRepo;
import com.overrideeg.apps.opass.service.files.FileStorageService;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import com.overrideeg.apps.opass.utils.FileStorageProperties;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class reportDetentionService extends AbstractService<reportDetention> {

    @Autowired
    TenantResolver tenantResolver;
    @Autowired
    private FileStorageService fileStorageService;


    public reportDetentionService(final reportDetentionRepo inRepository) {
        super(inRepository);
    }

    public reportDetention save(MultipartFile file, reportDetention reportDetention) {
        String fileName = null;
        String jasperPath;
        try {
            fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);
            String reportPath = new StringBuilder().append(FileStorageProperties.getUploadDir()).append("/").append(fileName).toString();
            jasperPath = new StringBuilder(reportPath.substring(0, reportPath.length() - 5)).append("jasper").toString();

            JasperCompileManager.compileReportToFile(
                    resource.getFile().getPath(), // the path to the jrxml file to compile
                    jasperPath); // the path and name we want to save the compiled file to

        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException(e.getMessage());
        }

        String fileNameJasper = new StringBuilder(file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 5)).append("jasper").toString();
        reportDetention.setFilePath(jasperPath);
        reportDetention.setSize(file.getSize());
        reportDetention.setFileName(file.getOriginalFilename());
        reportDetention.setJasperFileName(fileNameJasper);
        return mRepository.save(reportDetention);
    }


    public ResponseModel update(MultipartFile file, reportDetention reportDetention, Long reportId) throws NoSuchMethodException {
        String fileName = null;
        String jasperPath;
        try {
            fileName = fileStorageService.storeFile(file);
            Resource resource = fileStorageService.loadFileAsResource(fileName);
            String reportPath = new StringBuilder().append(FileStorageProperties.getUploadDir()).append("/").append(fileName).toString();
            jasperPath = new StringBuilder(reportPath.substring(0, reportPath.length() - 5)).append("jasper").toString();

            JasperCompileManager.compileReportToFile(
                    resource.getFile().getPath(), // the path to the jrxml file to compile
                    jasperPath); // the path and name we want to save the compiled file to

        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException(e.getMessage());
        }

        reportDetention storedReport = find(reportId).get();

        String fileNameJasper = new StringBuilder(file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 5)).append("jasper").toString();
        storedReport.setFilePath(jasperPath);
        storedReport.setSize(file.getSize());
        storedReport.setFileName(file.getOriginalFilename());
        storedReport.setJasperFileName(fileNameJasper);
        storedReport.setName(reportDetention.getName());
        return update(storedReport);
    }


    public HttpServletResponse generateReport(HttpServletResponse response, Long reportId, Long companyId, Map parameters) throws Exception {
        StringBuilder reportPath = new StringBuilder();
        String uploadDir = FileStorageProperties.getUploadDir();
        reportPath.append(uploadDir).append("/");
        reportDetention reportDetention = null;
        try {
            reportDetention = mRepository.findById(reportId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        reportPath.append(reportDetention.getFileName());

        Resource resource = fileStorageService.loadFileAsResource(reportDetention.getFileName());

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

//
        jasperPrintList.add(jasPrint);
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        exporter.exportReport();
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
        String username = config.getProperty("database.username");
        String password = config.getProperty("database.password");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "";
        Class.forName(driverClass);
        return DriverManager.getConnection(url, username, password);
    }


}