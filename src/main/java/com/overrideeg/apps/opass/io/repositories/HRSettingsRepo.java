package com.overrideeg.apps.opass.io.repositories;

import com.overrideeg.apps.opass.io.entities.HR.HRSettings;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface HRSettingsRepo extends JpaRepositoryCustomisations<HRSettings> {
}