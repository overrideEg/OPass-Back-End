/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.system.Connection;

import com.overrideeg.apps.opass.io.entities.User;
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
import java.sql.SQLException;
import java.util.ArrayList;
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
                    "SELECT c.database_name FROM User u \n" +
                            "INNER JOIN company c on c.id = u.company_id\n" +
                            " WHERE u.username  =  ?",
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
                    "SELECT c.id FROM User u INNER JOIN company c on c.id = u.company_id WHERE u.username =  ?", Long.class,
                    username);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public User findUserFromMasterDatabaseByUserName(String username) throws SQLException {
        String selectSQL = "select * from user u " +
                "left join user_roles ur on u.id = ur.user_id " +
                "where u.username =?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(selectSQL, username);
        User user = new User();
        if (maps.size() > 0) {
            Map<String, Object> result = maps.get(0);
            user.setId((Long) result.get("id"));
            List<String> roles = new ArrayList<>();
            Object rules = result.get("roles");
            roles.add((String) rules);
            user.setRoles(roles);
            user.setUsername((String) result.get("username"));
            user.setEmployee_id((Long) result.get("employee_id"));
            user.setMacAddress((String) result.get("mac_address"));
            user.setCompany_id((Long) result.get("company_id"));
            user.setImage((String) result.get("image"));
            user.setEmail((String) result.get("email"));
        }
        return user;
    }

    public User saveUserIntoMasterDatabase(User userToSave) {
        String idSql = "select max(id) from user";
        Long lastId = jdbcTemplate.queryForObject(idSql, Long.TYPE);
        Long newId = lastId + 1;
        String INSERT_SQL = "insert into user (id,username,email,company_id,password,mac_address,image)values(?,?,?,?,?,?,?)";
        int updatedId = jdbcTemplate.update(INSERT_SQL, newId, userToSave.getUsername(), userToSave.getEmail(),
                userToSave.getCompany_id(), userToSave.getPassword(), userToSave.getMacAddress(), userToSave.getImage());
        if (updatedId != 0) {
            String insetRolesSQL = "insert into user_roles (user_id, roles) values (?,?)";
            userToSave.getRoles().forEach(role -> {
                jdbcTemplate.update(insetRolesSQL, newId, role);
            });
        }
        String selectSQL = "select * from user u " +
                "left join user_roles ur on u.id = ur.user_id " +
                "where u.id =?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(selectSQL, newId);
        User user = new User();
        Map<String, Object> result = maps.get(0);
        user.setId((Long) result.get("id"));
        List<String> roles = new ArrayList<>();
        Object rules = result.get("roles");
        roles.add((String) rules);
        user.setRoles(roles);
        user.setUsername((String) result.get("username"));
        user.setMacAddress((String) result.get("mac_address"));
        user.setCompany_id((Long) result.get("company_id"));
        user.setEmployee_id((Long) result.get("employee_id"));
        user.setImage((String) result.get("image"));
        user.setEmail((String) result.get("email"));
        return user;
    }


    public Integer removeUser(Long userId) {
        return jdbcTemplate.update("delete from user where id = ?", userId);
    }

    public Integer updateUSerEmployeeId(Long user_id, Long employee_id) {
        return jdbcTemplate.update("update user set employee_id = ? where id = ?", employee_id, user_id);
    }
}