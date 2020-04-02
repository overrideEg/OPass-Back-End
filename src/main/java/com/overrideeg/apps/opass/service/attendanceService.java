/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.repositories.attendanceRepo;
import com.overrideeg.apps.opass.io.repositories.impl.attendanceRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class attendanceService extends AbstractService<attendance> {

    public attendanceService(final attendanceRepo inRepository) {
        super(inRepository);
    }

    @Autowired
    attendanceRepoImpl attendanceRepo;

    @Override
    public List<attendance> findAll(int start, int limit) {
        List<attendance> byDate = attendanceRepo.findByDate(new Date());
        return super.findAll(start, limit);
    }
}