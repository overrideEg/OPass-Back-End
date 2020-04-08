/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.UserRepo;
import com.overrideeg.apps.opass.io.repositories.employeeRepo;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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


    public employeeService(final employeeRepo inRepository) {
        super(inRepository);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    public employee save(employee inEntity, Long companyId) throws SQLException {
        boolean present = tenantResolver.findUserFromMasterDatabaseByUserName(inEntity.getContactInfo().getMobile()).getId() != null;
        this.resolveTenant.resolve(companyId, null);
        employee exitsEmployee = find("contactInfo.mobile", inEntity.getContactInfo().getMobile());
        if (present || exitsEmployee.isValid()) {
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }
        User createdUser = new User();
        this.resolveTenant.resolve(0L, null);
        try {
            // create user for new employee in master database
            createdUser = createUserForEmployee(inEntity, companyId);
        } catch (Exception e) {
            if (createdUser.getId() != null)
                this.tenantResolver.removeUser(createdUser.getId());
            throw new CouldNotCreateRecordException(e.getMessage());
        }

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
     * method that create user for employee in master database
     *
     * @param inEntity
     * @param companyId
     * @return User Created
     */
    private User createUserForEmployee(employee inEntity, Long companyId) {
        User user = new User();
        user.setUsername(inEntity.getContactInfo().getMobile());
        user.setPassword(passwordEncoder.encode(inEntity.getSsn()));
        user.setEmail(inEntity.getContactInfo().getEmail());
        company companyForTenantId = null;
        if (companyId != 0)
            companyForTenantId = tenantResolver.findCompanyForTenantId(companyId);
        user.setCompany_id(companyForTenantId.getId());
        List<String> rules = new ArrayList<>();
        rules.add(inEntity.getUserType().toString());
        user.setRoles(rules);
        return tenantResolver.saveUserIntoMasterDatabase(user);
    }


}