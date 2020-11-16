package com.overrideeg.apps.opass.io.repositories;


import com.overrideeg.apps.opass.io.entities.HR.HRSalary;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface HRSalaryRepo extends JpaRepositoryCustomisations<HRSalary> {
}