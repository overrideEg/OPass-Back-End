package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.repositories.attendanceRepo;
import org.springframework.stereotype.Service;

@Service
public class attendanceService extends AbstractService<attendance> {

    public attendanceService(final attendanceRepo inRepository) {
        super(inRepository);
    }

}