package com.overrideeg.apps.opass.io.repositories;

import com.overrideeg.apps.opass.io.entities.department;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface departmentRepo extends JpaRepositoryCustomisations<department> {
}