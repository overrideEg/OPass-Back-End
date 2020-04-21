/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service.HR;

import com.overrideeg.apps.opass.io.entities.HR.HRSettings;
import com.overrideeg.apps.opass.io.repositories.HRSettingsRepo;
import com.overrideeg.apps.opass.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HRSettingsService extends AbstractService<HRSettings> {

    public HRSettingsService ( final HRSettingsRepo inRepository ) {
        super(inRepository);
    }

    @Override
    public HRSettings save ( HRSettings inEntity ) {
        List<HRSettings> all = findAll();
        HRSettings settingsToSave;
        if (!all.isEmpty()) {
            settingsToSave = all.get(0);
            settingsToSave.setWorkOnDayOffAddition(inEntity.getWorkOnDayOffAddition());
            settingsToSave.setOverTimeAddition(inEntity.getOverTimeAddition());
            settingsToSave.setDelayHourDeduction(inEntity.getDelayHourDeduction());
            settingsToSave.setAbsenceDayDeduction(inEntity.getAbsenceDayDeduction());
        } else
            settingsToSave = inEntity;
        return super.save(settingsToSave);
    }
}