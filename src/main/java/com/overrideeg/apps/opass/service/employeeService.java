/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.exceptions.CouldNotDeleteRecordException;
import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.UserRepo;
import com.overrideeg.apps.opass.io.repositories.employeeRepo;
import com.overrideeg.apps.opass.io.repositories.impl.employeeRepoImpl;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class employeeService extends AbstractService<employee> {

    @Autowired
    companyService companyService;
    @Autowired
    TenantResolver tenantResolver;
    @Autowired
    UserRepo users;
    @Autowired
    ResolveTenant resolveTenant;
    @Autowired
    employeeRepoImpl employeeRepo;


    public employeeService ( final employeeRepo inRepository ) {
        super(inRepository);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    public employee save(employee inEntity, Long companyId) throws SQLException {

        inEntity.updateDateTime = new Date().getTime();
        // check user for employee in master database
        checkUserForEmployee(inEntity);
        // resolve tenant to company database to check exists employee
        this.resolveTenant.resolve(companyId, null);
        checkExistsEmployee(inEntity);
        // create user for employee
        User createdUser = new User();
        this.resolveTenant.resolve(0L, null);
        createdUser = createUser(inEntity, companyId);
        // set created user
        inEntity.setCreatedUserId(createdUser.getId());
        // return back saved employee
        employee saved = null;
        try {
            this.resolveTenant.resolve(companyId, null);
            saved = super.save(inEntity);
            // update user in master tenant
            this.resolveTenant.resolve(0L, null);
            this.tenantResolver.updateUSerEmployeeId(createdUser.getId(), saved.getId());
//            usersService.save(createdUser);
        } catch (Exception e) {
            e.printStackTrace();
            this.resolveTenant.resolve(0L, null);

            tenantResolver.removeUser(createdUser.getId());
            throw new CouldNotCreateRecordException(e.getMessage());
        }
        return saved;
    }

    /**
     * @param inEntity employee to create user for it
     * @param companyId
     * @return
     */
    private User createUser(employee inEntity, Long companyId) {
        User createdUser = new User();
        try {
            // create user for new employee in master database
            createdUser = createUserForEmployee(inEntity, companyId);
        } catch (Exception e) {
            if (createdUser.getId() != null)
                this.tenantResolver.removeUser(createdUser.getId());
            throw new CouldNotCreateRecordException(e.getMessage());
        }
        return createdUser;
    }


    /**
     * @param inEntity user To Search
     * @throws SQLException
     */
    private void checkUserForEmployee(employee inEntity) throws SQLException {
        User existUserForEmployee = tenantResolver.findUserFromMasterDatabaseByUserName(inEntity.getContactInfo().getMobile());
        if (existUserForEmployee.getId() != null)
            throw new CouldNotCreateRecordException("User For Employee are Exists: " + existUserForEmployee.getUsername());
    }


    /**
     * @param inEntity employee to search
     *
     */
    private void checkExistsEmployee(employee inEntity) {
        employee employee = find("mobile", inEntity.getContactInfo().getMobile());
//        String employeeQuery= "select e from employee e \n" +
//                "where e.contactInfo.mobile=:mobile or e.contactInfo.email=:email or e.ssn=:ssn";
//        List<String> attributeNames = new ArrayList<>(Arrays.asList("mobile","email","ssn"));
//        List attributeValues = new ArrayList(Arrays.asList(inEntity.getContactInfo().getMobile(),inEntity.getContactInfo().getEmail(),inEntity.getSsn()));
//        List<employee> employees = createQuery(employeeQuery, attributeNames, attributeValues);
//        employee exitsEmployee = new employee();
//        if (employees.size()>0) {
//            exitsEmployee = employees.get(0);
//        }
        if (employee.isValid()) {
            throw new CouldNotCreateRecordException("Employee username and SSN and email must be unique, employee exist: " + employee.getName().getEn());
        }
    }


    /**
     * method that create user for employee in master database
     *
     * @param inEntity
     * @param companyId
     * @return User Created
     */
    private User createUserForEmployee ( employee inEntity, Long companyId ) {
        User user = new User();
        user.setUsername(inEntity.getContactInfo().getMobile());
        user.setPassword(passwordEncoder.encode(inEntity.getSsn()));
        user.setFullName(inEntity.getName());
        user.setEmail(inEntity.getContactInfo().getEmail());
        company companyForTenantId = new company();
        if (companyId != 0)
            companyForTenantId = tenantResolver.findCompanyForTenantId(companyId);
        else
            companyForTenantId.setId(0L);
        user.setCompany_id(companyForTenantId.getId());
        List<String> rules = new ArrayList<>();
        inEntity.getUserType().forEach(userType -> rules.add(userType.name()));
        user.setRoles(rules);
        return tenantResolver.saveUserIntoMasterDatabase(user);
    }


    @Override
    public ResponseModel update ( employee inEntity ) {
        inEntity.updateDateTime = new Date().getTime();
        return super.update(inEntity);
    }

    public ResponseModel delete ( Long inId, Long companyId ) {
        this.resolveTenant.resolve(companyId, null);
        Optional<employee> employee = find(inId);
        ResponseModel delete = new ResponseModel();
        if (employee.isPresent()) {
            employee existsEmployee = employee.get();
            try {
                delete = super.delete(inId);
                this.resolveTenant.resolve(0L, null);
                User existsUser = this.tenantResolver.findUserFromMasterDatabaseByUserName(existsEmployee.getContactInfo().getMobile());
                Integer integer = this.tenantResolver.removeUser(existsUser.getId());
            } catch (SQLException e) {
                e.printStackTrace();
                throw new CouldNotDeleteRecordException(e.getMessage());
            }
        }
        return delete;
    }


    public employee getEmployeeAtDate ( Long id, Date date ) {
        long time = date.getTime();
        List<employee> employees = employeeRepo.auditEmployee(id);

        List<employee> collect = employees
                .stream()
                .filter(employee -> employee.updateDateTime.compareTo(time) > 0).collect(Collectors.toList());
        employee returnValue = new employee();
        if (collect.size() > 0) {
            return collect.get(collect.size() - 1);
        } else {
            return find(id).get();
        }
    }
}