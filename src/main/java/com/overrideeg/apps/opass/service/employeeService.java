package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.employeeRepo;
import org.springframework.stereotype.Service;


@Service
public class employeeService extends AbstractService<employee> {

    public employeeService(final employeeRepo inRepository) {
        super(inRepository);
    }

}