package com.overrideeg.apps.opass;

import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisationsImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EntityScan(basePackages = {
        "com.overrideeg.apps.opass.io.entities"
        , "com.overrideeg.apps.opass.io.details"})
@EnableJpaRepositories(basePackages = {"com.overrideeg.apps.opass.io.repositories"},
        repositoryBaseClass = JpaRepositoryCustomisationsImpl.class)
public class opassApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(opassApplication.class, args);
    }

}
