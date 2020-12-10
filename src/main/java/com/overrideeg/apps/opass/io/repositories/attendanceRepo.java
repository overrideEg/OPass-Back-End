package com.overrideeg.apps.opass.io.repositories;

import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface attendanceRepo extends JpaRepositoryCustomisations<attendance> {
}