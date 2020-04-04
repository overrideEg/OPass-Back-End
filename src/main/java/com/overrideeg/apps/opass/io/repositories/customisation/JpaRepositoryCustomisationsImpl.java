/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.repositories.customisation;


import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.system.Caching.OCacheManager;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import com.overrideeg.apps.opass.utils.JPAUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@NoRepositoryBean
public class JpaRepositoryCustomisationsImpl<T> extends SimpleJpaRepository<T, Long> implements
        JpaRepositoryCustomisations<T> {
    /* Constant(s): */

    /* Instance variable(s): */
    protected EntityManager mEntityManager;
    private Class<T> inEntityType;

    /**
     * Creates a repository instance for the entity specified by the supplied entity
     * information that uses the supplied entity manager.
     * Constructor from superclass.
     *
     * @param inEntityInformation Entity information.
     * @param inEntityManager     Entity manager.
     */
    public JpaRepositoryCustomisationsImpl(final JpaEntityInformation<T, ?> inEntityInformation,
                                           final EntityManager inEntityManager) {
        super(inEntityInformation, inEntityManager);
        mEntityManager = inEntityManager;
        this.inEntityType = inEntityInformation.getJavaType();
    }


    /**
     * Creates a repository instance for the supplied entity type that uses the
     * supplied entity manager.
     * Constructor from superclass.
     *
     * @param inEntityType    Entity type.
     * @param inEntityManager Entity manager.
     */
    public JpaRepositoryCustomisationsImpl(final Class<T> inEntityType,
                                           final EntityManager inEntityManager) {
        super(inEntityType, inEntityManager);
        mEntityManager = inEntityManager;
    }


    @Override
    public List<T> findAll(int start, int limit) {
        List<T> searchResults = null;
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(this.inEntityType);
        Root<T> root = criteria.from(this.inEntityType);
        criteria.select(root);


        searchResults = mEntityManager.createQuery(criteria).setFirstResult(start).setMaxResults(limit).
                getResultList();

        for (T result : searchResults) {
            JPAUtils.forceLoadLazyCollections(this.inEntityType, result);
        }

        return searchResults;
    }

    @Override
    public <S extends T> S save(S entity) {
        return super.save(entity);
    }


    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(entity -> {
        });
        return super.saveAll(entities);
    }


    @Override
    public T persist(T inEntity) {
        return super.save(inEntity);
    }

    @Override
    public Optional<T> findById(Long aLong) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(this.inEntityType);
        Root<T> root = criteria.from(this.inEntityType);
        criteria.select(root);
        Long company = (Long) OCacheManager.getInstance().get("company");
        if (company != null && company != 1)
            criteria.where(cb.equal(root.get("company"), company));
        criteria.where(cb.equal(root.get("id"), aLong));
        Optional<T> searchResult = Optional.empty();
        try {
            searchResult = Optional.ofNullable(mEntityManager.createQuery(criteria).
                    getSingleResult());
        } catch (Exception e) {
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        return searchResult;
    }

    @Override
    public T findByField(final String name, final Object value) {
        T Entity = null;
        try {
            Entity = this.inEntityType.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(this.inEntityType);
        Root<T> root = criteria.from(this.inEntityType);
        criteria.select(root);
        if (!name.contains("."))
            criteria.where(cb.equal(root.get(name), value));
        else {
            String firstObject = name.substring(0, name.indexOf("."));
            String secondObject = name.substring(name.indexOf(".") + 1);
            criteria.where(cb.equal(root.get(firstObject).get(secondObject), value));
        }
        // Fetch single result
        Query query = mEntityManager.createQuery(criteria);
        List<T> resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {

            Entity = resultList.get(0);
        }

        return Entity;
    }

    @Override
    public T findBySomeFields(final List<String> names, final List values) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(this.inEntityType);
        Root<T> root = criteria.from(this.inEntityType);
        criteria.select(root);
        Predicate[] predicates = new Predicate[names.size()];

        for (int i = 0; i < names.size(); i++) {
            predicates[i] = cb.equal(root.get(names.get(i)), values.get(i));
        }
        criteria.select(root).where(cb.or(predicates));

        // Fetch single result
        Query query = mEntityManager.createQuery(criteria);
        List singleResult = query.getResultList();

        T returnValue = null;
        if (singleResult.size() > 0) {
            returnValue = (T) singleResult.get(0);
        }
        return returnValue;
    }

    @Override
    public List<T> findWhere(final List<String> names, final List values) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(this.inEntityType);
        Root<T> root = criteria.from(this.inEntityType);
        criteria.select(root);
        Predicate[] predicates = new Predicate[names.size()];

        for (int i = 0; i < names.size(); i++) {
            predicates[i] = cb.equal(root.get(names.get(i)), values.get(i));
        }
        criteria.select(root).where(cb.and(predicates));

        // Fetch single result
        Query query = mEntityManager.createQuery(criteria);
        List<T> returnValue = query.getResultList();
        return returnValue;
    }


    @Override
    public List<T> createQuery(final String queryString, final List<String> whereNames, final List whereValues) {
        List<T> resultList = new ArrayList<>();
        try {
            Query q = mEntityManager.createQuery(queryString).unwrap(Query.class);
            for (int i = 0; i < whereNames.size(); i++) {
                q.setParameter(whereNames.get(i), whereValues.get(i));
            }
            resultList = q.getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List createGeneralQuery(final String queryString, final List<String> whereNames, final List whereValues) {
        List<T> resultList = new ArrayList<>();
        try {
            Query q = mEntityManager.createQuery(queryString).unwrap(Query.class);
            for (int i = 0; i < whereNames.size(); i++) {
                q.setParameter(whereNames.get(i), whereValues.get(i));
            }
            resultList = q.getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return resultList;
    }


    @Override
    public List<T> createQuery(final String queryString) {

        List<T> resultList = new ArrayList<>();
        try {
            Query q = mEntityManager.createQuery(queryString);
            resultList = q.getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return resultList;

    }

    @Override
    public List<T> createSQLQuery(final String queryString, final List<String> names, final List values) {

        List<T> resultList = new ArrayList<>();
        try {
            Query nativeQuery = mEntityManager.createNativeQuery(queryString, this.inEntityType);
            for (int i = 0; i < names.size(); i++) {
                nativeQuery.setParameter(names.get(i), values.get(i));
            }

            resultList = nativeQuery.getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<T> createSQLQuery(final String queryString) {

        List<T> resultList = new ArrayList<>();
        try {
            Query nativeQuery = mEntityManager.createNativeQuery(queryString, this.inEntityType);


            resultList = nativeQuery.getResultList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<T> findAllByField(String name, Object value) {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(this.inEntityType);
        Root<T> root = criteria.from(this.inEntityType);
        criteria.select(root);
        criteria.where(cb.equal(root.get(name), value));
        // Fetch single result
        Query query = mEntityManager.createQuery(criteria);
        return query.getResultList();
    }


}
