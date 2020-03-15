package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.appConstants.termsAndConditions;
import com.overrideeg.apps.opass.io.repositories.termsAndConditionsRepo;
import org.springframework.stereotype.Service;

@Service
public class termsAndConditionsService extends AbstractService<termsAndConditions> {

    public termsAndConditionsService(final termsAndConditionsRepo inRepository) {
        super(inRepository);
    }

}