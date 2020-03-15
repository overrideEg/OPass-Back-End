package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.attendanceRules;
import com.overrideeg.apps.opass.io.repositories.attendanceRulesRepo;
import org.springframework.stereotype.Service;

@Service
public class attendanceRulesService extends AbstractService<attendanceRules> {

    public attendanceRulesService(final attendanceRulesRepo inRepository) {
        super(inRepository);
    }

}