/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;


import com.overrideeg.apps.opass.exceptions.CouldNotDeleteRecordException;
import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.repositories.UserRepo;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class UserService {
    EntityUtils userProfileUtils = new EntityUtils();

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EntityManager mEntityManager;

    public User save(User requestUser) {
        String encode = this.passwordEncoder.encode(requestUser.getPassword());
        requestUser.setPassword(encode);
        User save = userRepo.save(requestUser);
        return save;
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(NoRecordFoundException::new);
    }

    public List<User> findAll(Long company_id) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.where(cb.equal(root.get("company_id"), company_id));
        criteriaQuery.select(root);
        TypedQuery<User> UserTypedQuery = mEntityManager.createQuery(criteriaQuery);
        return UserTypedQuery.getResultList();
    }

    public User update(User requestUser) {
        String encode = this.passwordEncoder.encode(requestUser.getPassword());
        requestUser.setPassword(encode);
        return userRepo.save(requestUser);
    }

    public void delete(Long id) {
        userRepo.delete(userRepo.findById(id).orElseThrow(CouldNotDeleteRecordException::new));
    }


}