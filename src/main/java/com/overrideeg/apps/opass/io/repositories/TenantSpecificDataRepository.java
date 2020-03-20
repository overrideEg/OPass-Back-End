/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.repositories;

import com.overrideeg.apps.opass.io.entities.TenantSpecificData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantSpecificDataRepository extends JpaRepository<TenantSpecificData, Long> {

    List<TenantSpecificData> findByUsername(String username);

    List<TenantSpecificData> findByCurrentTenantId(Long currentTenantId);

}
