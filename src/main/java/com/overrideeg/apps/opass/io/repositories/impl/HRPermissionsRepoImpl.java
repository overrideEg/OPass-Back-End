package com.overrideeg.apps.opass.io.repositories.impl;

import com.overrideeg.apps.opass.io.entities.HR.HRPermissions;
import com.overrideeg.apps.opass.io.entities.officialHoliday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * User: amr
 * Date: 4/27/2020
 * Time: 2:10 AM
 */
@Repository
public class HRPermissionsRepoImpl {
    @Autowired
    protected EntityManager mEntityManager;


    public HRPermissions employeeTodaysHRPermissions(Date currentDate) {
        try {
            CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
            CriteriaQuery<HRPermissions> query = cb.createQuery(HRPermissions.class);
            Root<HRPermissions> root = query.from(HRPermissions.class);

            cb.equal(root.get("fromDate"), cb.literal(currentDate));
            query.select(root);

            TypedQuery<HRPermissions> officialHolidayTypedQuery = mEntityManager.createQuery(query);

            return officialHolidayTypedQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

}