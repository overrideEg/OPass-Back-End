package com.overrideeg.apps.opass.service;

import com.overrideeg.apps.opass.io.entities.appConstants.appSetting;
import com.overrideeg.apps.opass.io.repositories.appSettingRepo;
import org.springframework.stereotype.Service;

@Service
public class appSettingService extends AbstractService<appSetting> {

    public appSettingService(final appSettingRepo inRepository) {
        super(inRepository);
    }

}