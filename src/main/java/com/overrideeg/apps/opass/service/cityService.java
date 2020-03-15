package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.city;
import com.overrideeg.apps.opass.io.repositories.cityRepo;
import org.springframework.stereotype.Service;
@Service
public class cityService extends AbstractService<city> {

    public cityService(final cityRepo inRepository) {
        super(inRepository);
    }

}