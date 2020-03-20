/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.system.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@Component
public class TenantResolver {

    @Autowired
    @Qualifier("masterDataSource")
    public DataSource masterDataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        jdbcTemplate = new JdbcTemplate(masterDataSource);
    }

    public String findDataBaseNameByTenantId(Long tenantId) {
        if (tenantId == null)
            return null;
        try {
            return jdbcTemplate.queryForObject("SELECT database_name FROM tenant WHERE tenant_id = ?", String.class, tenantId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String findDataBaseNameByUsername(String username) {
        if (username == null)
            return null;

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT t.database_name FROM user_tenant ut INNER JOIN tenant t on t.id = ut.tenant_id WHERE ut.user_name =  ?",
                    String.class, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public Long findTenantIdByUsername(String username) {
        if (username == null)
            return null;
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT t.tenant_id FROM user_tenant ut INNER JOIN tenant t on t.id = ut.tenant_id WHERE ut.user_name =  ?", Long.class,
                    username);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}