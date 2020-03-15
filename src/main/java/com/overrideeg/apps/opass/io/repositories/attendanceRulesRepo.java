package com.overrideeg.apps.opass.io.repositories;

import com.overrideeg.apps.opass.io.entities.attendanceRules;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface attendanceRulesRepo extends JpaRepositoryCustomisations<attendanceRules> {
}