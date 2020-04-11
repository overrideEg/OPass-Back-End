/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;


import com.overrideeg.apps.opass.exceptions.CouldNotDeleteRecordException;
import com.overrideeg.apps.opass.exceptions.CouldNotUpdateRecordException;
import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.repositories.UserRepo;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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

    @Transactional
    public User update(User requestUser) {
        try {
            User user = findById(requestUser.getId());
            requestUser.setEmail(user.getEmail());
            requestUser.setUsername(user.getUsername());
            requestUser.setPassword(user.getPassword());
            requestUser.setEmployee_id(user.getEmployee_id());
            requestUser.setRoles(user.getRoles());
            return userRepo.save(requestUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CouldNotUpdateRecordException(e.getMessage());
        }
    }

    @Transactional
    public User updateUserImage(User requestUser) {
        try {
            User user = findById(requestUser.getId());
            requestUser.setEmail(user.getEmail());
            requestUser.setUsername(user.getUsername());
            requestUser.setPassword(user.getPassword());
            requestUser.setEmployee_id(user.getEmployee_id());
            requestUser.setMacAddress(user.getMacAddress());
            requestUser.setRoles(user.getRoles());
            return userRepo.save(requestUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CouldNotUpdateRecordException(e.getMessage());
        }
    }

    public User update(Long userId, String userMacAddress) {
        User user = userRepo.findById(userId).orElseThrow(NoRecordFoundException::new);
        user.setMacAddress(userMacAddress);
        return userRepo.save(user);
    }

    public void delete(Long id) {
        userRepo.delete(userRepo.findById(id).orElseThrow(CouldNotDeleteRecordException::new));
    }


}