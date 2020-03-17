package com.overrideeg.apps.opass.io.repositories;

import com.overrideeg.apps.opass.io.entities.city;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface cityRepo extends JpaRepositoryCustomisations<city> {
}