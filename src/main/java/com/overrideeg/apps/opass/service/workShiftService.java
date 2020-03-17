package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.io.repositories.workShiftRepo;
import org.springframework.stereotype.Service;


@Service
public class workShiftService extends AbstractService<workShift> {

    public workShiftService(final workShiftRepo inRepository) {
        super(inRepository);
    }

}