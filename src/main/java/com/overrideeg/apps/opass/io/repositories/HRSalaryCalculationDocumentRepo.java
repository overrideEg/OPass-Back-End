package com.overrideeg.apps.opass.io.repositories;


import com.overrideeg.apps.opass.io.entities.HR.HRSalaryCalculationDocument;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface HRSalaryCalculationDocumentRepo extends JpaRepositoryCustomisations<HRSalaryCalculationDocument> {
}