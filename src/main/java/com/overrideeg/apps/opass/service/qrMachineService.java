package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.qrMachine;
import com.overrideeg.apps.opass.io.repositories.qrMachineRepo;
import org.springframework.stereotype.Service;

@Service
public class qrMachineService extends AbstractService<qrMachine> {

    public qrMachineService(final qrMachineRepo inRepository) {
        super(inRepository);
    }

}