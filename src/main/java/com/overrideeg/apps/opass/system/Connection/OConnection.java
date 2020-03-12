package com.overrideeg.apps.opass.system.Connection;


import com.overrideeg.apps.opass.exceptions.DatabaseException;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OConnection {
    public static Properties getProperties() {
        Properties properties = new Properties();
        try {
            InputStream input = OConnection.class.getClassLoader().getResourceAsStream("application.properties");

            if (input == null) {
                throw new DatabaseException(ErrorMessages.DatabaseError.getErrorMessage());
            }
            Properties config = new Properties();
            // load a properties file
            config.load(input);

            properties.put("driver", config.getProperty("spring.datasource.driver-class-name"));
            properties.put("url", config.getProperty("spring.datasource.url"));
            properties.put("user", config.getProperty("spring.datasource.username"));
            properties.put("password", config.getProperty("spring.datasource.password"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }

    private static Connection createConnection() throws SQLException {
        Properties props = getProperties();
        return DriverManager.getConnection(props.getProperty("spring.datasource.url"), props);
    }

    public static Connection dbConn() {
        Connection connection = null;
        try {
             connection = createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection ;
    }
}

