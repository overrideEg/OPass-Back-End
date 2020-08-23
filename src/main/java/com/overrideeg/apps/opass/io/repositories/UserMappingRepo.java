package com.overrideeg.apps.opass.io.repositories;

import com.overrideeg.apps.opass.io.entities.UserMapping;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMappingRepo extends JpaRepositoryCustomisations<UserMapping> {
}