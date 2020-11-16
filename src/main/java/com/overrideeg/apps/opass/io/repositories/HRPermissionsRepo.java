package com.overrideeg.apps.opass.io.repositories;


import com.overrideeg.apps.opass.io.entities.HR.HRPermissions;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface HRPermissionsRepo extends JpaRepositoryCustomisations<HRPermissions> {
}