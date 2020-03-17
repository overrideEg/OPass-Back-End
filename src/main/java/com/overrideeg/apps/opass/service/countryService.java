package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.country;
import com.overrideeg.apps.opass.io.repositories.countryRepo;
import org.springframework.stereotype.Service;


@Service
public class countryService extends AbstractService<country> {

    public countryService(final countryRepo inRepository) {
        super(inRepository);
    }

}