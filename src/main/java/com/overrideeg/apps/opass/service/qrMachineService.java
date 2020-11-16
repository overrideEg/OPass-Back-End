/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.qrMachine;
import com.overrideeg.apps.opass.io.repositories.impl.qrMachineRepoImpl;
import com.overrideeg.apps.opass.io.repositories.qrMachineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class qrMachineService extends AbstractService<qrMachine> {

    @Autowired
    qrMachineRepoImpl qrMachineRepo;

    public qrMachineService(final qrMachineRepo inRepository) {
        super(inRepository);
    }

    public List<qrMachine> findQrMachineForDepartmentAndBranch ( Long departmentId, Long branchId ) {
        return qrMachineRepo.findQrMachineForDepartmentAndBranch(departmentId, branchId);
    }
}