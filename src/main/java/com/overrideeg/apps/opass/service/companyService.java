package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.enums.gender;
import com.overrideeg.apps.opass.enums.userType;
import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.companyRepo;
import com.overrideeg.apps.opass.system.Connection.ResolveTenant;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class companyService extends AbstractService<company> {
    @Autowired
    ResolveTenant resolveTenant;
    @Autowired
    EntityManager entityManager;
    @Autowired
    employeeService employeeService;

    public companyService ( final companyRepo inRepository ) {
        super(inRepository);
    }

    @Override
    public company save ( company inEntity ) {
        resolveTenant.resolve(0L, null);
        company company = super.save(inEntity);

        Thread thread = new Thread(() -> {
            TenantContext.setCurrentTenant(company.getId());
            employee employee = new employee();
            employee.setName(company.getName());
            employee.setEmail(company.getWebsite() != null ? company.getWebsite() : company.getPhoneNumber());
            employee.setMobile(company.getPhoneNumber());
            employee.setUserType(Collections.singletonList(userType.companyOwner));
            employee.setGender(gender.EMPTY);
            employee.setJobTitle("Company Super Admin Account");
            employee.setTenant(company.getId());
            employeeService.save(employee);
            System.out.println(employee.toString());
        });
        thread.start();


        return company;
    }


    @Transactional
    public company updateUserCompany ( company company ) {
        resolveTenant.resolve(0L, null);
        company exists = find(company.getId()).get();
        company.setDatabase_name(exists.getDatabase_name());
        company.setDescription(exists.getDescription());
        company.setEnabled(exists.getEnabled());
        company.setCountry(exists.getCountry());
        return this.entityManager.merge(company);
    }
}