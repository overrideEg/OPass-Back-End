package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.subscription;
import com.overrideeg.apps.opass.io.repositories.subscriptionRepo;
import org.springframework.stereotype.Service;


@Service
public class subscriptionService extends AbstractService<subscription> {

    public subscriptionService(final subscriptionRepo inRepository) {
        super(inRepository);
    }

}