package com.overrideeg.apps.opass.io.repositories;

import com.overrideeg.apps.opass.io.entities.qrMachine;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisations;
import org.springframework.stereotype.Repository;

@Repository
public interface qrMachineRepo extends JpaRepositoryCustomisations<qrMachine> {
}