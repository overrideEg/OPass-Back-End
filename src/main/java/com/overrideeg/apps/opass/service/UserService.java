/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;


import com.overrideeg.apps.opass.enums.userType;
import com.overrideeg.apps.opass.exceptions.CouldNotDeleteRecordException;
import com.overrideeg.apps.opass.exceptions.CouldNotUpdateRecordException;
import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.io.entities.User;
import com.overrideeg.apps.opass.io.entities.UserMapping;
import com.overrideeg.apps.opass.io.repositories.UserRepo;
import com.overrideeg.apps.opass.system.Caching.OCacheManager;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import com.overrideeg.apps.opass.system.Connection.TenantResolver;
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
import java.util.stream.Collectors;

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


    public User save ( User requestUser ) {
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

    public List<User> findAll(Long company_id) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.where(cb.equal(root.get("company_id"), company_id));
        criteriaQuery.select(root);
        TypedQuery<User> UserTypedQuery = mEntityManager.createQuery(criteriaQuery);
        return UserTypedQuery.getResultList().stream()
                .filter(user -> user.getRoles().stream().anyMatch(s -> !s.equals(userType.user.toString())))
                .collect(Collectors.toList());

    }

    @Transactional
    public User update(User requestUser) {
        try {
            User byId = findById(requestUser.getId());
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

    public User update ( Long userId ) {
        User user = userRepo.findById(userId).orElseThrow(NoRecordFoundException::new);
        return userRepo.save(user);
    }

    public void delete ( Long id ) {
        userRepo.delete(userRepo.findById(id).orElseThrow(CouldNotDeleteRecordException::new));
    }


    public User update ( Long userId, String fcmToken ) {
        User user = userRepo.findById(userId).orElseThrow(NoRecordFoundException::new);
        user.setFcmToken(fcmToken);
        return userRepo.save(user);
    }

    public User findByUserNameOrMobile ( String email, String mobile ) {
        String queryString = "select u from User u where u.username=:userName or u.email=:userName or u.username=:mobile or u.email=:mobile";
        List resultList = this.mEntityManager.createQuery(queryString).setParameter("userName", email).setParameter("mobile", mobile).getResultList();
        if (resultList.size() > 0)
            return (User) resultList.get(0);

        return new User();
    }

    public User findByUserName ( String email ) {
        String queryString = "select u from User u where u.username=:userName or u.email=:userName";
        List resultList = this.mEntityManager.createQuery(queryString).setParameter("userName", email).getResultList();
        if (resultList.size() > 0)
            return (User) resultList.get(0);
        return new User();
    }


}