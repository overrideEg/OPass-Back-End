package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.company;
import com.overrideeg.apps.opass.io.repositories.companyRepo;
import org.springframework.stereotype.Service;
@Service
public class companyService extends AbstractService<company> {

    public companyService(final companyRepo inRepository) {
        super(inRepository);
    }

}