/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service.HR;

import com.overrideeg.apps.opass.io.entities.HR.HRPermissions;
import com.overrideeg.apps.opass.io.entities.officialHoliday;
import com.overrideeg.apps.opass.io.repositories.HRPermissionsRepo;
import com.overrideeg.apps.opass.io.repositories.impl.HRPermissionsRepoImpl;
import com.overrideeg.apps.opass.io.repositories.impl.officialHolidayRepoImpl;
import com.overrideeg.apps.opass.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HRPermissionsService extends AbstractService<HRPermissions> {

    public HRPermissionsService ( final HRPermissionsRepo inRepository ) {
        super(inRepository);
    }

    @Autowired
    HRPermissionsRepoImpl hrPermissionsRepoImpl;

    @Override
    public HRPermissions save ( HRPermissions inEntity ) {
        return super.save(inEntity);
    }


    public List<HRPermissions> getHRPermissionsInCurrentDate (Date currentDate , Long employee_id) {
        return hrPermissionsRepoImpl.employeeTodaysHRPermissions(currentDate, employee_id);
    }


/*    @Override
    public ResponseModel update ( HRPermissions inEntity ) {
        throw new CouldNotUpdateRecordException("HR Permission Could Not Update");
    }

    @Override
    public List<ResponseModel> update ( List<HRPermissions> inEntity ) {
        throw new CouldNotUpdateRecordException("HR Permission Could Not Update");
    }

    @Override
    public ResponseModel delete ( Long inId ) {
        throw new CouldNotDeleteRecordException("HR Permission Could Not Delete");
    }
*/
}