package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.employeeRepo;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class employeeService extends AbstractService<employee> {
    @Autowired
    UsersService usersService;
    @Autowired
    companyService companyService;
    @Autowired
    TenantResolver tenantResolver;

    public employeeService(final employeeRepo inRepository) {
        super(inRepository);
    }


    @Override
    public employee save(employee inEntity, Long companyId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        TenantContext.setCurrentTenant(companyId);
        Users createdUser = null;
        try {
            // create user for new employee in master database
            createdUser = createUserForEmployee(inEntity, companyId);
        } catch (Exception e) {
            e.printStackTrace();
            if (createdUser.getId() != null)
                usersService.delete(createdUser.getId());
            throw new CouldNotCreateRecordException(e.getMessage());
        }

        // set created user
        inEntity.setCreatedUserId(createdUser.getId());
        // return back saved employee
        employee saved = null;
        try {
            saved = super.save(inEntity);
        } catch (Exception e) {
            e.printStackTrace();
            usersService.delete(createdUser.getId());
            throw new CouldNotCreateRecordException(e.getMessage());

        }
        return saved;
    }


    /**
     * method that create user for employee in master database
     *
     * @param inEntity
     * @param companyId
     * @return User Created
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Users createUserForEmployee(employee inEntity, Long companyId) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Users users = new Users();
        users.setUserName(companyId + "." + inEntity.getContactInfo().getMobile());
        users.setPassword(inEntity.getSsn());
        users.setEmail(inEntity.getContactInfo().getEmail());
        company companyForTenantId = tenantResolver.findCompanyForTenantId(companyId);
        users.setCompany_id(companyForTenantId.getId());
        users.setUserType(inEntity.getUserType());
        return usersService.save(users);
    }


    //    @Value("${report.path}")
    private String reportPath;

    public String generateReport() {
        List<employee> employees = new ArrayList<>();
        mRepository.findAll().stream().forEach(e -> employees.add(e));
        try {

            File file = ResourceUtils.getFile("classpath:employee-rpt.jrxml");
            InputStream input = new FileInputStream(file);
            // Compile the Jasper report from .jrxml to .japser
            JasperReport jasperReport = JasperCompileManager.compileReport(input);
            // Get your data source
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);
            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "JavaHelper.org");
            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);
            // Export the report to a PDF file
            JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "\\Empployee.pdf");
            System.out.println("PDF File Generated !!");
            JasperExportManager.exportReportToXmlFile(jasperPrint, reportPath + "\\Employee.xml", true);
            System.out.println("XML File Generated !!");
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportPath + "\\Employee.html");
            System.out.println("HTML Generated");
            xlsx(jasperPrint);
            csv(jasperPrint);
            return "Report successfully generated @path= " + reportPath;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private void csv(JasperPrint jasperPrint) throws JRException {
        JRCsvExporter exporter = new JRCsvExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(reportPath + "\\Employee.csv"));
        SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
        configuration.setFieldDelimiter(",");
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }

    // Ref: https://www.programcreek.com/java-api-examples/?class=net.sf.jasperreports.export.SimpleXlsxReportConfiguration&method=setOnePagePerSheet
    private void xlsx(JasperPrint jasperPrint) throws JRException {
        // Exports a JasperReports document to XLSX format. It has character output type and exports the document to a grid-based layout.
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportPath + "\\Employee.xlsx"));
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(true);
        configuration.setRemoveEmptySpaceBetweenColumns(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
}