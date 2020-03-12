package com.overrideeg.apps.opass.service;


import com.overrideeg.apps.opass.annotations.Local;
import com.overrideeg.apps.opass.exceptions.CouldNotDeleteRecordException;
import com.overrideeg.apps.opass.exceptions.CouldNotUpdateRecordException;
import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.io.entity.System.Localized;
import com.overrideeg.apps.opass.io.entity.System.OEntity;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import com.overrideeg.apps.opass.ui.sys.RequestOperation;
import com.overrideeg.apps.opass.ui.sys.ResponseModel;
import com.overrideeg.apps.opass.ui.sys.ResponseStatus;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Abstract base class for services that has operations for creating, reading,
 * updating and deleting entities.
 * This implementation uses RxJava.
 *
 * @param <E> Entity type.
 * @author Ivan Krizsan
 */
@Transactional
public abstract class AbstractService<E extends OEntity, L> {
    /* Constant(s): */

    /* Instance variable(s): */
    protected JpaRepositoryCustomisations<E> mRepository;
    private final Class<L> localizedClass;

    /**
     * Creates a service instance that will use the supplied repository for entity persistence.
     *
     * @param inRepository Entity repository.
     */
    public AbstractService(final JpaRepositoryCustomisations<E> inRepository) {
        mRepository = inRepository;
        this.localizedClass = (Class<L>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[1];
    }


    /**
     * Saves the supplied entity.
     *
     * @param inEntity Entity to save.
     * @param lang
     * @return Observable that will receive the saved entity, or exception if error occurs.
     */
    public E save(final E inEntity, String lang) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        if (localizedClass != null) {
            localize(inEntity,lang);
        }
        return mRepository.persist(inEntity);
    }

    protected  void localize(E inEntity, String lang) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Field> LocalizedFields = EntityUtils.findAnnotatedFields(inEntity.getClass(), Local.class);
        //todo add forign
//            List<Field> ForignFields = EntityUtils.findAnnotatedFields(inEntity.getClass(), Forign.class);
        // try to put localized fields onto hash map
        L localizedClass = this.localizedClass.getDeclaredConstructor().newInstance();
        LocalizedFields.forEach(field -> {
            EntityUtils.runSetter(field, localizedClass, EntityUtils.runGetter(field, inEntity));
        });
        Localized localized = new Localized(lang);
        EntityUtils.runSetter(localizedClass.getClass().getDeclaredField("localized"), localizedClass, localized);
        Map<String, L> localizationObject = new HashMap<>();
        localizationObject.put(lang, localizedClass);
        Field relationField = EntityUtils.findAnnotatedFields(localizedClass.getClass(), Local.class).get(0);
        EntityUtils.runSetter(inEntity.getClass().getDeclaredField("localizations"), inEntity, localizationObject);
        EntityUtils.runSetter(relationField, localizedClass, inEntity);
    }


//    Product p = new Product();
//p.setPrice(19.99D);
//
//    LocalizedProduct lpDe = new LocalizedProduct();
//lpDe.setId(new LocalizedId("de"));
//lpDe.setProduct(p);
//lpDe.setName("Hibernate Tips - Mehr als 70 Lösungen für typische Hibernateprobleme");
//p.getLocalizations().put("de", lpDe);
//
//    LocalizedProduct lpEn = new LocalizedProduct();
//lpEn.setId(new LocalizedId("en"));
//lpEn.setProduct(p);
//lpEn.setName("Hibernate Tips - More than 70 solution to common Hibernate problems");
//p.getLocalizations().put("en", lpEn);
//
//em.persist(p);

    public List<E> saveArray(final List<E> inEntity) {
        List<E> entities = new ArrayList<>();
        inEntity.forEach(e -> {
            try {
                entities.add(save(e, null));
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchFieldException ex) {
                ex.printStackTrace();
            }
        });
        return entities;
    }

    /**
     * Updates the supplied entity.
     *
     * @param inEntity Entity to update.
     * @return Observable that will receive the updated entity, or exception if error occurs.
     */
    public ResponseModel update(final E inEntity) throws NoSuchMethodException {
        ResponseModel responseModel = null;
        try {
            E persist = mRepository.persist(inEntity);
            responseModel = new ResponseModel(persist, RequestOperation.UPDATE, ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseModel = new ResponseModel(inEntity, RequestOperation.UPDATE, ResponseStatus.ERROR);
        }

        return responseModel;

    }

    public List<ResponseModel> update(final List<E> inEntity) throws NoSuchMethodException {
        List<ResponseModel> out = new ArrayList<>();
        try {
            inEntity.forEach(e -> {
                try {
                    mRepository.persist(e);
                    out.add(new ResponseModel(e, RequestOperation.UPDATE, ResponseStatus.SUCCESS));
                } catch (NoSuchMethodException ex) {
                    ex.printStackTrace();
                    out.add(new ResponseModel(e, RequestOperation.UPDATE, ResponseStatus.ERROR));
                }
            });
        } catch (Exception e) {
            throw new CouldNotUpdateRecordException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage());
        }

        return out;
    }

    /**
     * Finds the entity having supplied id.
     *
     * @param inEntityId Id of entity to retrieve.
     * @return Observable that will receive the found entity, or exception if
     * error occurs or no entity is found.
     */
    @Transactional(readOnly = true)
    public Optional<E> find(Long inEntityId) {
        Optional<E> byId;
        try {
            byId = mRepository.findById(inEntityId);
        } catch (Exception e) {
            throw new NoRecordFoundException(e.getMessage());

        }
        return byId;
    }


    @Transactional(readOnly = true)
    public E find(String by, Object value) {
        return mRepository.findByField(by, value);
    }

    @Transactional(readOnly = true)
    public E find(List<String> names, List values) {
        return mRepository.findBySomeFields(names, values);
    }

    @Transactional(readOnly = true)
    public List<E> findWhere(List<String> names, List values) {
        return mRepository.findWhere(names, values);
    }

    /**
     * Finds all the entities.
     *
     * @return Observable that will receive a list of entities, or exception if error occurs.
     */
    @Transactional(readOnly = true)
    public List<E> findAll(int start, int limit) {
        List<E> theEntitiesList = null;
        try {
            theEntitiesList = mRepository.findAll(start, limit);

        } catch (final Exception theException) {
            theException.printStackTrace();
        }
        return theEntitiesList;

    }

    /**
     * Deletes the entity having supplied id.
     *
     * @param inId Id of entity to delete.
     * @return Observable that will receive completion, or exception if error occurs.
     */
    public ResponseModel delete(final Long inId) {
        ResponseModel responseModel = null;

        try {
            mRepository.deleteById(inId);

            responseModel = new ResponseModel(inId, RequestOperation.DELETE, ResponseStatus.SUCCESS);
        } catch (final Exception e) {
            responseModel = new ResponseModel(inId, RequestOperation.DELETE, ResponseStatus.ERROR);
            throw new CouldNotDeleteRecordException(ErrorMessages.COULD_NOT_CREATE_RECORD.getErrorMessage());
        }
        return responseModel;
    }


    public List<E> createQuery(final String queryString, final List<String> whereNames, final List whereValues) {
        return mRepository.createQuery(queryString, whereNames, whereValues);
    }

    public List<E> createQuery(final String queryString) {
        return mRepository.createQuery(queryString);
    }


    public List<E> createSQLQuery(final String queryString, final List<String> whereNames, final List whereValues) {
        return mRepository.createSQLQuery(queryString, whereNames, whereValues);
    }


    public List createGeneralQuery(final String queryString, final List<String> whereNames, final List whereValues) {
        return mRepository.createGeneralQuery(queryString, whereNames, whereValues);
    }


    public List<E> createSQLQuery(final String queryString) {
        return mRepository.createSQLQuery(queryString);
    }


}
