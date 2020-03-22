package com.overrideeg.apps.opass.io.repositories;

import com.overrideeg.apps.opass.io.entities.appConstants.appSetting;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface appSettingRepo extends JpaRepositoryCustomisations<appSetting> {
}