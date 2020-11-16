/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.repositories.impl;

import com.overrideeg.apps.opass.io.entities.workShift;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class workShiftRepoImpl {

    @Autowired
    protected EntityManager mEntityManager;

    public List<workShift> auditiedWorkShift ( Long id ) {
        return (List<workShift>) AuditReaderFactory
                .get(mEntityManager).createQuery()
                .forRevisionsOfEntity(workShift.class, true, true)
                .add(AuditEntity.id().eq(id))
                .getResultList();
    }


}
