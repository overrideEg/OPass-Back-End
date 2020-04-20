/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.repositories.impl;

import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class attendanceRepoImpl {

    @Autowired
    protected EntityManager mEntityManager;


    public List<attendance> findEmployeeTodaysShitLogs(employee employee, Date currentDate, workShift currentShift) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<attendance> query = cb.createQuery(attendance.class);
        Root<attendance> root = query.from(attendance.class);
        query.select(root);
        Predicate[] predicates = new Predicate[3];
        predicates[0] = cb.equal(root.get("scanDate"), currentDate);
        predicates[1] = cb.equal(root.get("workShift").get("id"), currentShift.getId());
        predicates[2] = cb.equal(root.get("employee").get("id"), employee.getId());

        query.select(root).where(cb.and(predicates));

        return mEntityManager.createQuery(query).getResultList();

    }

    public List<attendance> createAttendanceHistoryReport(Long employee, Integer page, Integer pageSize) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<attendance> criteriaQuery = cb.createQuery(attendance.class);
        Root<attendance> root = criteriaQuery.from(attendance.class);
        criteriaQuery.where(cb.equal(root.get("employee").get("id"), employee));
        criteriaQuery.select(root);
        criteriaQuery.orderBy(cb.desc(root.get("scanDate")), cb.desc(root.get("scanTime")));
        TypedQuery<attendance> attendanceTypedQuery = mEntityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize);
        List<attendance> resultList = new ArrayList<>();
        try {
            resultList = attendanceTypedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public Long findAbsenceDays(Long employeeId) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<attendance> criteriaQuery = cb.createQuery(attendance.class);
        Root<attendance> root = criteriaQuery.from(attendance.class);
        criteriaQuery.where(cb.equal(root.get("employee").get("id"), employeeId));
        criteriaQuery.select(root);
        criteriaQuery.orderBy(cb.desc(root.get("scanDate")));
        long between = 0;
        try {
            attendance attendance = mEntityManager.createQuery(criteriaQuery).setMaxResults(1).getResultList().get(0);
            between = new DateUtils().calculateDaysBetweenTwoDates(attendance.getScanDate(), new Date());
        } catch (NoResultException e) {
        }

        return between;
    }

    public List findTotalEmployeeAttendanceFromFirstMonth(Date fromDate, Date toDate) {
//        String query = "select a.scanDate,count(a) from attendance a\n" +
//                "where a.scanDate between :fromDate and :toDate and a.attType != 'LOG' \n" +
//                "group by a.scanDate";
        String query = "";

        List resultList = mEntityManager.createQuery(query)
                .setParameter("fromDate", fromDate).setParameter("toDate", toDate)
                .getResultList();


        return resultList;
    }
}
