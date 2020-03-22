package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.enums.userType;
import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.employeeRepo;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
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

    public employeeService(final employeeRepo inRepository) {
        super(inRepository);
    }


    @Override
    public employee save(employee inEntity, Long companyId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Users createdUser = null;
        try {
            // create user for new employee in master database
            createdUser = createUserForEmployee(inEntity, companyId);
        } catch (Exception e) {
            throw new CouldNotCreateRecordException(ErrorMessages.COULD_NOT_CREATE_RECORD.getErrorMessage());
        }

        //return to tenant database
        TenantContext.setCurrentTenant(companyId);
        // set created user
        inEntity.setCreatedUserId(createdUser.getId());
        // return back saved employee
        return super.save(inEntity);
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
        TenantContext.setCurrentTenant(null);
        Users users = new Users();
        users.setUserName(inEntity.getContactInfo().getMobile());
        users.setPassword(inEntity.getSSN());
        users.setEmail(inEntity.getContactInfo().getEmail());
        users.setCompany(companyService.find(companyId).get());
        users.setUserType(userType.user);
        return usersService.save(users);
    }
}