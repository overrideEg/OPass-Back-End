/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.repositories.impl;

import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.io.entities.qrMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class qrMachineRepoImpl {
    @Autowired
    protected EntityManager mEntityManager;

    public qrMachine findQrMachineForDepartmentAndBranch(Long departmentId, Long branchId) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<qrMachine> query = cb.createQuery(qrMachine.class);
        Root<qrMachine> root = query.from(qrMachine.class);
        query.select(root);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.equal(root.get("branch").get("id"), branchId);
        predicates[1] = cb.equal(root.get("department").get("id"), departmentId);
        query.select(root).where(cb.and(predicates));
        qrMachine result;
        try {
            List<qrMachine> resultList = mEntityManager.createQuery(query).getResultList();
            result = resultList.stream().findFirst().orElseThrow(NoRecordFoundException::new);
        } catch (Exception e) {
            throw new NoRecordFoundException(e.getMessage());
        }
        return result;
    }
}
