package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.department;
import com.overrideeg.apps.opass.io.repositories.departmentRepo;
import org.springframework.stereotype.Service;


@Service
public class departmentService extends AbstractService<department> {

    public departmentService(final departmentRepo inRepository) {
        super(inRepository);
    }

}