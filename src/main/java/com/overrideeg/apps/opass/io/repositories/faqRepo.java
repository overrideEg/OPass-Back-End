package com.overrideeg.apps.opass.io.repositories;


import com.overrideeg.apps.opass.io.entities.appConstants.faq;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface faqRepo extends JpaRepositoryCustomisations<faq> {
}