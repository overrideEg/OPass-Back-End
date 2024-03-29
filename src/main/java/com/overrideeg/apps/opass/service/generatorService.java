/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.exceptions.QrIllegalException;
import com.overrideeg.apps.opass.io.entities.qrMachine;
import com.overrideeg.apps.opass.ui.entrypoint.generator.generatorRequest;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class generatorService {
    @Autowired
    qrMachineService qrMachineService;

    public qrMachine validate(generatorRequest request) {
        qrMachine qrMachine = qrMachineService.find(request.getQrMachine().getId()).get();
        if (!qrMachine.getDepartment().getId().equals(request.getDepartment().getId())) {
            throw new QrIllegalException(ErrorMessages.DEPARTMENT_is_invalid.getErrorMessage());
        }
        if (!qrMachine.getBranch().getId().equals(request.getBranch().getId())) {
            throw new QrIllegalException(ErrorMessages.Branch_is_invalid.getErrorMessage());
        }
        if ((qrMachine.getMacAddress() != null && !qrMachine.getMacAddress().equals("") )&& !qrMachine.getMacAddress().equals(request.getMacAddress())) {
            throw new QrIllegalException(ErrorMessages.MAC_ADDRESS_ILLEGAL.getErrorMessage());
        }

        qrMachine.setMacAddress(request.getMacAddress());
        qrMachineService.update(qrMachine);
        return qrMachine;
    }
}
