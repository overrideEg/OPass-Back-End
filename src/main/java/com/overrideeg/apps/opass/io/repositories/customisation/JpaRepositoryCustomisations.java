package com.overrideeg.apps.opass.io.repositories.customisation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * Interface defining custom method(s) added to all the Spring Data JPA repositories
 * in the application.
 *
 * @param <T> Entity type.
 * @author Ivan Krizsan
 */
@NoRepositoryBean
public interface JpaRepositoryCustomisations<T> extends JpaRepository<T, Long> {
    List <T> findAll(int start, int limit);

    @Override
    <S extends T> S save(S entity);

    /**
     * Persists the supplied entity.
     * If the entity has an id and previously has been persisted, it will be merged
     *  to the persistence context otherwise it will be inserted into the
     *  persistence context.
     *
     * @param inEntity Entity to persist.
     * @return Persisted entity.
     */


    T persist ( T inEntity ) throws NoSuchMethodException;

    T findByField ( String name, Object value );


    List<T> findListByField ( String name, Object value );


    Optional<T> findBySomeFields ( List<String> names, List values );

    List<T> findWhere ( List<String> names, List values );

    List<T> createQuery ( String queryString, List<String> whereNames, List whereValues );

    List createGeneralQuery ( String queryString, List<String> whereNames, List whereValues );

    List<T> createQuery(String queryString);

    List<T> createSQLQuery(String queryString, List<String> names, List values);

    List<T> createSQLQuery(String queryString);

    List<T> findAllByField(String name, Object value);

    @Override
    Optional <T> findById(Long aLong);
}
