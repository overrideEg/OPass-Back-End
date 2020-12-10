package com.overrideeg.apps.opass.io.repositories;


import com.overrideeg.apps.opass.io.entities.reports.reportCategory;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface reportCategoryRepo extends JpaRepositoryCustomisations<reportCategory> {
}