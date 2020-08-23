/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.system.Connection;

import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.entities.country;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import com.overrideeg.apps.opass.io.valueObjects.translatedLob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;


@Component
public class TenantResolver {

    @Autowired
    @Qualifier("masterDataSource")
    public DataSource masterDataSource;
    private JdbcTemplate jdbcTemplate;


    @PostConstruct
    private void init () {
        jdbcTemplate = new JdbcTemplate(masterDataSource);
    }

    public String findDataBaseNameByTenantId ( Long tenantId ) {
        if (tenantId == null)
            return null;
        try {
            return jdbcTemplate.queryForObject("SELECT database_name FROM company WHERE id = ? and enabled = 1", String.class, tenantId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public company findCompanyForTenantId ( Long tenantId ) {
        if (tenantId == null)
            return null;
        company company = new company();
        try {
            List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT * FROM company WHERE id = ? ", tenantId);
            Map<String, Object> result = maps.get(0);
            company.setId((Long) result.get("id"));
            company.setDatabase_name((String) result.get("database_name"));
            company.setDescription(new translatedLob((String) result.get("description_ar"), (String) result.get("description_en"), (String) result.get("description_tr")));
            company.setName(new translatedField((String) result.get("name_ar"), (String) result.get("name_en"), (String) result.get("name_tr")));
            company.setEnabled((Boolean) result.get("enabled"));
            company.setPhoneNumber((String) result.get("phone_number"));
            company.setWebsite((String) result.get("website"));
            company.setCountry(new country((Long) result.get("country_id")));
            company.setImage((String) result.get("image"));


        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return company;
    }

    public Integer mapUser ( Long companyId, String username ) {
        String idSql = "select max(id) from user_mapping";
        Long lastId = jdbcTemplate.queryForObject(idSql, Long.TYPE);
        Long newId = lastId != null ? lastId + 1 : 1;
        return jdbcTemplate.update("insert into user_mapping (username, company_id, id) values (?,?,?)", username, companyId, newId);
    }

    public Long findCompanyIdForUser ( String username ) {
        return jdbcTemplate.queryForObject("select company_id from user_mapping where username =?", Long.TYPE, username);
    }

    public Long findCompanyIdForUser ( Long userid ) {
        return jdbcTemplate.queryForObject("select company_id from user_mapping where userid =?", Long.TYPE, userid);
    }


    public int deleteUserMapping ( String username ) {
        return jdbcTemplate.update("delete from user_mapping where username = ? ", username);
    }
}