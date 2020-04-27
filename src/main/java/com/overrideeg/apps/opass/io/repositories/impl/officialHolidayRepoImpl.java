package com.overrideeg.apps.opass.io.repositories.impl;

import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.entities.officialHoliday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * User: amr
 * Date: 4/27/2020
 * Time: 2:10 AM
 */
@Repository
public class officialHolidayRepoImpl {
    @Autowired
    protected EntityManager mEntityManager;


    public List<officialHoliday> findofficialHoliday (Date currentDate) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<officialHoliday> query = cb.createQuery(officialHoliday.class);
        Root<officialHoliday> root = query.from(officialHoliday.class);

//        Predicate[] predicates = new Predicate[2];
//
//       predicates[0]= cb.greaterThanOrEqualTo(root.get("fromDate"),currentDate);
//        predicates[1]= cb.lessThanOrEqualTo(root.get("toDate"),currentDate);

        cb.between(cb.literal(currentDate), root.get("fromDate"),root.get("toDate"));
        query.select(root);


        TypedQuery<officialHoliday> officialHolidayTypedQuery = mEntityManager.createQuery(query);
        return officialHolidayTypedQuery.getResultList();
    }

}