/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.employeeRepo;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;


@Service
public class employeeService extends AbstractService<employee> {
    @Autowired
    UsersService usersService;
    @Autowired
    companyService companyService;
    @Autowired
    TenantResolver tenantResolver;

    public employeeService(final employeeRepo inRepository) {
        super(inRepository);
    }



    public employee save(employee inEntity, Long companyId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Users exitsUser = usersService.find("userName", companyId + "." + inEntity.getContactInfo().getMobile());
        employee exitsEmployee = find("contactInfo.mobile", inEntity.getContactInfo().getMobile());
        if (exitsUser.isValid() || exitsEmployee.isValid()) {
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }
        Users createdUser = new Users();
        try {
            // create user for new employee in master database
            createdUser = createUserForEmployee(inEntity, companyId);
        } catch (Exception e) {
            if (createdUser.getId() != null)
                usersService.delete(createdUser.getId());
            throw new CouldNotCreateRecordException(e.getMessage());
        }

        // set created user
        inEntity.setCreatedUserId(createdUser.getId());
        // return back saved employee
        employee saved = null;
        try {
            saved = super.save(inEntity);
            createdUser.setEmployee(saved);
            usersService.update(createdUser);
        } catch (Exception e) {
            e.printStackTrace();
            usersService.delete(createdUser.getId());
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
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Users createUserForEmployee(employee inEntity, Long companyId) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Users users = new Users();
        users.setUserName(companyId + "." + inEntity.getContactInfo().getMobile());
        users.setPassword(inEntity.getSsn());
        users.setEmail(inEntity.getContactInfo().getEmail());
        company companyForTenantId = null;
        if (companyId != 0)
            companyForTenantId = tenantResolver.findCompanyForTenantId(companyId);
        users.setCompany_id(companyForTenantId.getId());
        users.setUserType(inEntity.getUserType());
        return usersService.save(users);
    }


}