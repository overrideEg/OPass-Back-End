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


    public List<HRPermissions> employeeTodaysHRPermissions(Date currentDate, Long employee_id) {
        try {
            String query = "select pr from HRPermissions pr \n" +
                    "where pr.date<= :date and pr.employee.id=:employee_id";
            return mEntityManager.createQuery(query, HRPermissions.class).setParameter("date", currentDate)
                    .setParameter("employee_id", employee_id).getResultList();

        } catch (Exception e) {
            System.err.println("error getting hr permissions"+e.getMessage());
            return null;
        }

    }

}