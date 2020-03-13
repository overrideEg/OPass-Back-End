package com.overrideeg.apps.opass.io.repositories.system;

import com.overrideeg.apps.opass.io.entities.system.Users;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepositoryCustomisations <Users> {
}