package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.reports.reportCategory;
import com.overrideeg.apps.opass.io.repositories.reportCategoryRepo;
import org.springframework.stereotype.Service;

@Service
public class reportCategoryService extends AbstractService<reportCategory> {

    public reportCategoryService(final reportCategoryRepo inRepository) {
        super(inRepository);
    }

}