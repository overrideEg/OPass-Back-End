/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;


import com.overrideeg.apps.opass.exceptions.CouldNotDeleteRecordException;
import com.overrideeg.apps.opass.exceptions.CouldNotUpdateRecordException;
import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.entities.UserMapping;
import com.overrideeg.apps.opass.io.repositories.UserRepo;
import com.overrideeg.apps.opass.system.Caching.OCacheManager;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EntityManager mEntityManager;
    @Autowired
    TenantResolver tenantResolver;
    @Autowired
    UserMappingService userMappingService;

    public User save(User requestUser) {
        String encode = this.passwordEncoder.encode(requestUser.getPassword());
        requestUser.setPassword(encode);
        User save = userRepo.save(requestUser);
        Long tenantId = (Long) OCacheManager.getInstance().get("tenantId");
        Thread thread = new Thread(() -> {
            TenantContext.setCurrentTenant(null);
            UserMapping map = new UserMapping();
            map.setCompanyId(requestUser.getTenant() != null ? requestUser.getTenant() : tenantId);
            map.setUsername(requestUser.getUsername());
            this.userMappingService.save(map);
        });
        thread.start();
        return save;
    }


    @Transactional
    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(NoRecordFoundException::new);
    }



    @Transactional
    public User update(User requestUser) {
        try {
            User byId = findById(requestUser.getId());
            byId.setMacAddress(requestUser.getMacAddress());
            BeanUtils.copyProperties(byId,requestUser);
            requestUser.setEmployee(byId.getEmployee());
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
//            requestUser.setEmployee_id(user.getEmployee_id());
            requestUser.setRoles(user.getRoles());
            return userRepo.save(requestUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CouldNotUpdateRecordException(e.getMessage());
        }
    }

    public User update(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(NoRecordFoundException::new);
        return userRepo.save(user);
    }

    public void delete(Long id) {
        userRepo.delete(userRepo.findById(id).orElseThrow(CouldNotDeleteRecordException::new));
    }


    public User findByUserNameOrMobile(String email, String mobile)   {
        String queryString = "select u from User u where u.username=:userName or u.email=:userName or u.username=:mobile or u.email=:mobile";
        return (User) this.mEntityManager.createQuery(queryString).setParameter("userName", email).setParameter("mobile", mobile).getResultList().stream().findFirst().orElse(new User());
    }

    public User findByUserName(String email)   {
        String queryString = "select u from User u where u.username=:userName or u.email=:userName";
        return (User) this.mEntityManager.createQuery(queryString).setParameter("userName", email).getResultList().stream().findFirst().orElse(new User());

    }


    public List<User> findAll() {
        return this.userRepo.findAll();
    }
}