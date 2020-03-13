/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service.entityServices;


import com.overrideeg.apps.opass.io.entities.city;
import com.overrideeg.apps.opass.io.repositories.cityRepo;
import com.overrideeg.apps.opass.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class cityService extends AbstractService<city> {

    public cityService(final cityRepo inRepository) {
        super(inRepository);
    }

}