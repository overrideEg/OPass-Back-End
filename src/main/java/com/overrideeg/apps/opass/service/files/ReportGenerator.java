/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service.files;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

public class ReportGenerator {

    public static void main(String[] args) {
        HashMap hm = null;
        // System.out.println("Usage: ReportGenerator ....");

        try {
            System.out.println("Start ....");
            // Get jasper report
            String jrxmlFileName = "C:/reports/C1_report.jrxml";
            String jasperFileName = "C:/reports/C1_report.jasper";
            String pdfFileName = "C:/reports/C1_report.pdf";

            JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);

            // String dbUrl = props.getProperty("jdbc.url");
            String dbUrl = "jdbc:oracle:thin:@localhost:1521:mydbname";
            // String dbDriver = props.getProperty("jdbc.driver");
            String dbDriver = "oracle.jdbc.driver.OracleDriver";
            // String dbUname = props.getProperty("db.username");
            String dbUname = "mydb";
            // String dbPwd = props.getProperty("db.password");
            String dbPwd = "mydbpw";

            // Load the JDBC driver
            Class.forName(dbDriver);
            // Get the connection
            Connection conn = DriverManager
                    .getConnection(dbUrl, dbUname, dbPwd);

            // Create arguments
            // Map params = new HashMap();
            hm = new HashMap();
            hm.put("ID", "123");
            hm.put("DATENAME", "April 2006");

            // Generate jasper print
            JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, hm, conn);

            // Export pdf file
            JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);

            System.out.println("Done exporting reports to pdf");

        } catch (Exception e) {
            System.out.print("Exceptiion" + e);
        }
    }
}