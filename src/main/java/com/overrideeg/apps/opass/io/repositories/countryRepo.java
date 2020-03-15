package com.overrideeg.apps.opass.io.repositories;

import com.overrideeg.apps.opass.io.entities.country;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface countryRepo extends JpaRepositoryCustomisations<country> {
}