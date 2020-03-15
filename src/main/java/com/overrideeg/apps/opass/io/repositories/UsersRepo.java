/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.repositories;

import com.overrideeg.apps.opass.io.entities.Users;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepositoryCustomisations<Users> {
}