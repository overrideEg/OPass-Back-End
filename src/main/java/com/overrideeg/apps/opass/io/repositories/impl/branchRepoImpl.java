/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.repositories.impl;

import com.overrideeg.apps.opass.io.entities.branch;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class branchRepoImpl {

    @Autowired
    protected EntityManager mEntityManager;

    public List<branch> auditBranch ( Long id ) {
        return (List<branch>) AuditReaderFactory
                .get(mEntityManager).createQuery()
                .forRevisionsOfEntity(branch.class, true, true)
                .add(AuditEntity.id().eq(id))
                .getResultList();
    }
}
