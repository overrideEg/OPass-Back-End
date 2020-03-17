package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.branch;
import com.overrideeg.apps.opass.io.repositories.branchRepo;
import org.springframework.stereotype.Service;

@Service
public class branchService extends AbstractService<branch> {

    public branchService(final branchRepo inRepository) {
        super(inRepository);
    }

}