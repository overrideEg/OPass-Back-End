/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.system.Connection;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MasterDatabaseMigrationConfig {

    @Autowired
    @Qualifier("masterDataSource")
    public DataSource masterDataSource;

    @Bean
    public Flyway flyway(@Qualifier("masterDataSource") DataSource theDataSource) {
        Flyway flyway = Flyway.configure().dataSource(masterDataSource)
                .locations("classpath:db/migration/master").baselineOnMigrate(true)
                .outOfOrder(true).load();
        flyway.repair();
        flyway.migrate();
        return flyway;
    }
}