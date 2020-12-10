/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.enums.userType;
import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.entities.UserMapping;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.employeeRepo;
import com.overrideeg.apps.opass.io.repositories.impl.employeeRepoImpl;
import com.overrideeg.apps.opass.system.Caching.OCacheManager;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import com.overrideeg.apps.opass.system.Mail.EmailService;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import com.overrideeg.apps.opass.ui.sys.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class employeeService extends AbstractService<employee> {
    @Autowired
    companyService companyService;
    @Autowired
    ResolveTenant resolveTenant;
    @Autowired
    EmailService emailService;
    @Autowired
    UserMappingService mappingService;
    @Autowired
    employeeRepoImpl employeeRepo;
    @Autowired
    EntityManager mEntityManager;
    @Autowired
    TenantResolver tenantResolver;

    public employeeService(final employeeRepo inRepository) {
        super(inRepository);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Override
    public employee save(employee inEntity) {

        if (inEntity.getUserType() == null)
            inEntity.setUserType(new ArrayList<>(Collections.singletonList(userType.user)));
        // check user for employee
        checkUserForEmployee(inEntity);
        // check employee exists
        checkExistsEmployee(inEntity);
        // create user for employee
        User createdUser = createUserForEmployee(inEntity);
        inEntity.setUser(createdUser);
        // set created user
        // return back saved employee
        employee saved;
        Long tenantId = (Long) OCacheManager.getInstance().get("tenantId");

        try {
            saved = super.save(inEntity);
            try {
                this.emailService.sendInvitationEmail(inEntity.getTenant() != null ? inEntity.getTenant() : tenantId, createdUser, saved);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // update user in master tenant

            try {
                Thread thread = new Thread(() -> {
                    TenantContext.setCurrentTenant(null);
                    UserMapping map = new UserMapping();
                    map.setCompanyId(inEntity.getTenant() != null ? inEntity.getTenant() : tenantId);
                    map.setUsername(saved.getUser().getUsername());
                    UserMapping mapping = this.mappingService.find("username", saved.getUser().getUsername());
                    if (mapping.isValid()) {
                        mapping.setCompanyId(inEntity.getTenant() != null ? inEntity.getTenant() : tenantId);
                        this.mappingService.update(map);
                    } else {
                        this.mappingService.save(map);
                    }
                });
                thread.start();
            } catch (Exception e) {
                delete(createdUser.getId());
                throw new CouldNotCreateRecordException(e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CouldNotCreateRecordException(e.getMessage());
        }
        return saved;
    }


    /**
     * @param inEntity user To Search
     */
    private void checkUserForEmployee(employee inEntity) {

        User existUserForEmployee = new User();
        System.out.println("employee"+inEntity.toString());
         existUserForEmployee = this.userService.findByUserNameOrMobile(inEntity.getEmail(), inEntity.getMobile());
        System.out.println("User"+ existUserForEmployee.toString());
        if (existUserForEmployee.getId() != null)
            throw new CouldNotCreateRecordException("User For Employee are Exists: " + existUserForEmployee.getUsername());
    }


    /**
     * @param inEntity employee to search
     */
    private void checkExistsEmployee(employee inEntity) {
        String query = "select e from employee e where e.email=:email or e.mobile=:mobile or e.email=:mobile or e.mobile=:email";
        employee employee = new employee();
        employee = (com.overrideeg.apps.opass.io.entities.employee) mEntityManager.createQuery(query).setParameter("email", inEntity.getEmail()).setParameter("mobile", inEntity.getMobile()).getResultList().stream().findFirst().orElse(new employee());
        if (employee.isValid()) {
            throw new CouldNotCreateRecordException("Employee username and SSN and email must be unique, employee exist: " + employee.getName().getEn());
        }
    }


    /**
     * method that create user for employee in master database
     *
     * @return User Created
     */
    private User createUserForEmployee(employee inEntity) {
        User user = new User();
        Long tenantId = (Long) OCacheManager.getInstance().get("tenantId");
        user.setUsername(inEntity.getMobile() != null ? inEntity.getMobile() : inEntity.getEmail());
        user.setPassword(passwordEncoder.encode(inEntity.getMobile() != null ? inEntity.getMobile() : inEntity.getEmail().substring(0, inEntity.getEmail().indexOf("@"))));
        user.setFullName(inEntity.getName());
        user.setEmail(inEntity.getEmail());
        user.setTenant(inEntity.getTenant() != null ? inEntity.getTenant() : tenantId);
        List<String> rules = new ArrayList<>();
        inEntity.getUserType().forEach(userType -> rules.add(userType.name()));
        user.setRoles(rules);
        return user;
    }


    @Override
    public ResponseModel delete(Long inId) {
        Optional<employee> employeeOptional = find(inId);
        ResponseModel delete = super.delete(inId);
        if (delete.getResponseStatus().equals(ResponseStatus.SUCCESS) && employeeOptional.isPresent())
            this.mappingService.deleteUserMapping(employeeOptional.get().getEmail());
        return delete;
    }

    @Override
    public ResponseModel update(employee inEntity) {
        Optional<employee> employeeOptional = this.find(inEntity.getId());
        if (employeeOptional.isPresent()) {
            User user = employeeOptional.get().getUser();
            List<String> rules = new ArrayList<>();
            inEntity.getUserType().forEach(userType -> rules.add(userType.name()));
            user.setRoles(rules);

            inEntity.setUser(user);
        }
        return super.update(inEntity);
    }

    public employee getEmployeeAtDate(Long id, Date date) {
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