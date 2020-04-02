/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.repositories.impl;

import com.overrideeg.apps.opass.io.entities.attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository
public class attendanceRepoImpl {

    @Autowired
    protected EntityManager mEntityManager;


    public List<attendance> findByDate(Date date) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<attendance> query = cb.createQuery(attendance.class);
        Root<attendance> root = query.from(attendance.class);
        query.select(root);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.equal(root.get("scanDate"), date);
        predicates[1] = cb.equal(root.get("workShift").get("id"), 1L);
        query.select(root).where(cb.and(predicates));
        List<attendance> resultList = mEntityManager.createQuery(query).getResultList();

        return resultList;
    }
}
