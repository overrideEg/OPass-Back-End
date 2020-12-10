package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.io.repositories.impl.workShiftRepoImpl;
import com.overrideeg.apps.opass.io.repositories.workShiftRepo;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class workShiftService extends AbstractService<workShift> {

    @Autowired
    workShiftRepoImpl workShiftRepo;

    public workShiftService ( final workShiftRepo inRepository ) {
        super(inRepository);
    }

    @Override
    public Optional<workShift> find ( Long inEntityId ) {
        return super.find(inEntityId);
    }


    @Override
    public workShift save ( workShift inEntity ) {
        inEntity.setUpdateDateTime(new Date().getTime());
        return super.save(inEntity);
    }

    @Override
    public ResponseModel update ( workShift inEntity ) {
        inEntity.setUpdateDateTime(new Date().getTime());
        return super.update(inEntity);
    }

    public workShift getWorkShiftAtDate ( Long id, Date date ) {
        long time = date.getTime();
        List<workShift> workShifts = workShiftRepo.auditiedWorkShift(id);

        List<workShift> collect = workShifts
                .stream()
                .filter(workShift -> workShift.getUpdateDateTime().compareTo(time) > 0).collect(Collectors.toList());
        workShift returnValue = new workShift();
        if (collect.size() > 0) {
            return collect.get(collect.size() - 1);
        } else {
            return find(id).get();
        }
    }
}