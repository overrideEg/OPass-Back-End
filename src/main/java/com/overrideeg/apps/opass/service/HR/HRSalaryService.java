/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service.HR;

import com.overrideeg.apps.opass.io.entities.HR.HRSalary;
import com.overrideeg.apps.opass.io.repositories.HRSalaryRepo;
import com.overrideeg.apps.opass.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class HRSalaryService extends AbstractService<HRSalary> {

    public HRSalaryService ( final HRSalaryRepo inRepository ) {
        super(inRepository);
    }

}