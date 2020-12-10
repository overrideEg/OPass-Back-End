package com.overrideeg.apps.opass.service;


import com.overrideeg.apps.opass.io.entities.UserMapping;
import com.overrideeg.apps.opass.io.repositories.UserMappingRepo;
import com.overrideeg.apps.opass.system.Connection.TenantContext;
import org.springframework.stereotype.Service;

@Service
public class UserMappingService extends AbstractService<UserMapping> {

    public UserMappingService ( final UserMappingRepo inRepository ) {
        super(inRepository);
    }


    public void deleteUserMapping ( String email ) {
        Thread thread = new Thread(() -> {
            TenantContext.setCurrentTenant(null);
            UserMapping mapping = find("username", email);
            delete(mapping.getId());
        });
        thread.start();
    }
}