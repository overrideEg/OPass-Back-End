/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.system.Connection;

import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.entities.country;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;


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
            return jdbcTemplate.queryForObject("SELECT database_name FROM company WHERE id = ?", String.class, tenantId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public company findCompanyForTenantId(Long tenantId) {
        if (tenantId == null)
            return null;
        company company = new company();
        try {
            List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT * FROM company WHERE id = ?", tenantId);
            Map<String, Object> result = maps.get(0);
            company.setId((Long) result.get("id"));
            company.setCreationDate((Date) result.get("creation_date"));
            company.setDatabase_name((String) result.get("database_name"));
            company.setDescription(new translatedField((String) result.get("description_ar"), (String) result.get("description_en"), (String) result.get("description_tr")));
            company.setName(new translatedField((String) result.get("name_ar"), (String) result.get("name_en"), (String) result.get("name_tr")));
            company.setEnabled((Boolean) result.get("enabled"));
            company.setPhoneNumber((String) result.get("phone_number"));
            company.setWebsite((String) result.get("website"));
            company.setCountry(new country((Long) result.get("country_id")));


        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return company;
    }

    public String findDataBaseNameByUsername(String username) {
        if (username == null)
            return null;

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT c.database_name FROM Users u \n" +
                            "INNER JOIN company c on c.id = u.company_id\n" +
                            " WHERE u.user_name  =  ?",
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
                    "SELECT c.id FROM Users u INNER JOIN company c on c.id = u.company_id WHERE u.user_name =  ?", Long.class,
                    username);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}