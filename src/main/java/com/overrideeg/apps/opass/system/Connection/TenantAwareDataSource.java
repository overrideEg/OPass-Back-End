/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.system.Connection;

import com.overrideeg.apps.opass.utils.DBUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class TenantAwareDataSource extends AbstractRoutingDataSource {

    @Autowired
    @Qualifier("masterDataSource")
    public DataSource masterDataSource;
    private Map<Object, DataSource> resolvedDataSources = new HashMap<>();
    @Autowired
    @Qualifier("masterConfig")
    private HikariConfig hikariConfig;

    @Autowired
    private TenantDatabaseMigrationService tenantDatabaseMigrationService;

    @Autowired
    private TenantResolver tenantResolver;

    @Override
    public void afterPropertiesSet() {
        super.setDefaultTargetDataSource(masterDataSource);
        super.setTargetDataSources(new HashMap<>());
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        Long tenantId = (Long) determineCurrentLookupKey();
        if (tenantId == null)
            return masterDataSource;

        DataSource tenantDataSource = resolvedDataSources.get(tenantId);
        if (tenantDataSource == null) {
            tenantDataSource = createDataSourceForTenantId(tenantId);
            tenantDatabaseMigrationService.flywayMigrate(tenantDataSource);
            resolvedDataSources.put(tenantId, tenantDataSource);
        }

        return tenantDataSource;
    }

    private DataSource createDataSourceForTenantId(Long tenantId) {
        String tenantDatabaseName = tenantResolver.findDataBaseNameByTenantId(tenantId);
        if (tenantDatabaseName == null)
            throw new IllegalArgumentException("Given tenant id is not valid : " + tenantId);

        HikariConfig tenantHikariConfig = new HikariConfig();
        hikariConfig.copyStateTo(tenantHikariConfig);
        String tenantJdbcURL = DBUtil.databaseURLFromMYSQLJdbcUrl(hikariConfig.getJdbcUrl(), tenantDatabaseName);
        tenantHikariConfig.setJdbcUrl(tenantJdbcURL);
        tenantHikariConfig.setPoolName(tenantDatabaseName + "-db-pool");
        return new HikariDataSource(tenantHikariConfig);
    }

}
