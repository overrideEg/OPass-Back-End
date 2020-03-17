package com.overrideeg.apps.opass.service;


import com.overrideeg.apps.opass.io.entities.appConstants.faq;
import com.overrideeg.apps.opass.io.repositories.faqRepo;
import org.springframework.stereotype.Service;

@Service
public class faqService extends AbstractService<faq> {

    public faqService(final faqRepo inRepository) {
        super(inRepository);
    }

}