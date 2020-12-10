package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.enums.employeeStatus;
import com.overrideeg.apps.opass.enums.userType;
import com.overrideeg.apps.opass.exceptions.CouldNotCreateRecordException;
import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.companyRepo;
import com.overrideeg.apps.opass.system.Caching.OCacheManager;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class companyService extends AbstractService<company> {

    @Autowired
    employeeService employeeService;
    @Autowired
    ResolveTenant resolveTenant;

    public companyService(final companyRepo inRepository) {
        super(inRepository);
    }

    @Override
    public company save(company inEntity) {
        inEntity.setEnabled(true);
        inEntity = super.save(inEntity);
        company finalInEntity = inEntity;
        AtomicReference<employee> employee = new AtomicReference<>(new employee());
        resolveTenant.resolve(inEntity.getId(), null);

        Thread t = new Thread(() -> {
            resolveTenant.resolve(finalInEntity.getId(),null);
            OCacheManager.getInstance().put("tenantId",finalInEntity.getId());
            employee.get().setEmail(finalInEntity.getEmail());
            employee.get().setMobile(finalInEntity.getPhoneNumber());
            employee.get().setUserType(new ArrayList<>(Arrays.asList(userType.companyOwner)));
            employee.get().setName(finalInEntity.getName());
            employee.get().setTenant(finalInEntity.getId());
            employee.get().setStatus(employeeStatus.active);
            employee.set(this.employeeService.save(employee.get()));
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new CouldNotCreateRecordException(e.getMessage());
        }
        if (!employee.get().isValid()) {
            delete(inEntity.getId());
            inEntity = new company();
        }

        if (!inEntity.isValid())
            throw new CouldNotCreateRecordException("Can Not Create Company");

        return inEntity;
    }
}