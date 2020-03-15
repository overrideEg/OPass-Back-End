package com.overrideeg.apps.opass;

import com.overrideeg.apps.opass.filters.AuthenticationFilter;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisationsImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableCaching
@EnableWebMvc
@EntityScan(basePackages = {
        "com.overrideeg.apps.opass.io.entities"
        , "com.overrideeg.apps.opass.io.details"})
@EnableJpaRepositories(basePackages = {"com.overrideeg.apps.opass.io.repositories"},
        repositoryBaseClass = JpaRepositoryCustomisationsImpl.class)
public class opassApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(opassApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationFilter());
    }
}
