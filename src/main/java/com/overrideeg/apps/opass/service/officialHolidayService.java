package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.officialHoliday;
import com.overrideeg.apps.opass.io.repositories.officialHolidayRepo;
import org.springframework.stereotype.Service;

@Service
public class officialHolidayService extends AbstractService<officialHoliday> {

    public officialHolidayService ( final officialHolidayRepo inRepository ) {
        super(inRepository);
    }

}