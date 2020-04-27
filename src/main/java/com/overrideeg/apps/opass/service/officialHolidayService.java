package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.entities.officialHoliday;
import com.overrideeg.apps.opass.io.repositories.impl.attendanceRepoImpl;
import com.overrideeg.apps.opass.io.repositories.impl.officialHolidayRepoImpl;
import com.overrideeg.apps.opass.io.repositories.officialHolidayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class officialHolidayService extends AbstractService<officialHoliday> {

    public officialHolidayService ( final officialHolidayRepo inRepository ) {
        super(inRepository);
    }

    @Autowired
    officialHolidayRepoImpl officialHolidayRepo;


    public List<officialHoliday> getOfficialHollidaysInCurrentDate (Date currentDate ) {
        return officialHolidayRepo.findofficialHoliday(currentDate);
    }
}