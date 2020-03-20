/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.system.Connection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class TenantDatabaseConfig {

    @Bean(name = "tenantAwareDataSource")
    @Primary
    public DataSource tenantAwareDataSource() {
        return new TenantAwareDataSource();
    }

}