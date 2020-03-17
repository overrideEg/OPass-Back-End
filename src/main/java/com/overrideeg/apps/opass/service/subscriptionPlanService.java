package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.subscriptionPlan;
import com.overrideeg.apps.opass.io.repositories.subscriptionPlanRepo;
import org.springframework.stereotype.Service;

@Service
public class subscriptionPlanService extends AbstractService<subscriptionPlan> {

    public subscriptionPlanService(final subscriptionPlanRepo inRepository) {
        super(inRepository);
    }

}