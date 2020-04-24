/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service.HR;

import com.overrideeg.apps.opass.exceptions.CouldNotDeleteRecordException;
import com.overrideeg.apps.opass.exceptions.CouldNotUpdateRecordException;
import com.overrideeg.apps.opass.io.entities.HR.HRPermissions;
import com.overrideeg.apps.opass.io.repositories.HRPermissionsRepo;
import com.overrideeg.apps.opass.service.AbstractService;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HRPermissionsService extends AbstractService<HRPermissions> {

    public HRPermissionsService ( final HRPermissionsRepo inRepository ) {
        super(inRepository);
    }

    @Override
    public HRPermissions save ( HRPermissions inEntity ) {
        return super.save(inEntity);
    }

    @Override
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

}