/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.repositories.impl;

import com.overrideeg.apps.opass.io.entities.department;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class departmentRepoImpl {
    @Autowired
    protected EntityManager mEntityManager;

    public List<department> auditDepartment ( Long id ) {
        return (List<department>) AuditReaderFactory
                .get(mEntityManager).createQuery()
                .forRevisionsOfEntity(department.class, true, true)
                .add(AuditEntity.id().eq(id))
                .getResultList();
    }
}
