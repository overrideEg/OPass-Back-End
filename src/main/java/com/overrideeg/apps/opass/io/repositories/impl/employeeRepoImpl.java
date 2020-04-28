/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.repositories.impl;

import com.overrideeg.apps.opass.io.entities.employee;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class employeeRepoImpl {

    @Autowired
    protected EntityManager mEntityManager;

    public List<employee> auditEmployee ( Long id ) {
        return (List<employee>) AuditReaderFactory
                .get(mEntityManager).createQuery()
                .forRevisionsOfEntity(employee.class, true, true)
                .add(AuditEntity.id().eq(id))
                .getResultList();
    }
}
