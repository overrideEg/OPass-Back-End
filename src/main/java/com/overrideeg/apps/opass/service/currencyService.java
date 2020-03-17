package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.currency;
import com.overrideeg.apps.opass.io.repositories.currencyRepo;
import org.springframework.stereotype.Service;


@Service
public class currencyService extends AbstractService<currency> {

    public currencyService(final currencyRepo inRepository) {
        super(inRepository);
    }

}