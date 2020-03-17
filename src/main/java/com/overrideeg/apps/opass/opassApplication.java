package com.overrideeg.apps.opass;

import com.overrideeg.apps.opass.filters.AuthenticationFilter;
import com.overrideeg.apps.opass.io.repositories.customisation.JpaRepositoryCustomisationsImpl;
import com.overrideeg.apps.opass.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
@EnableCaching
@EnableWebMvc
@EntityScan(basePackages = {
        "com.overrideeg.apps.opass.io.entities"
        , "com.overrideeg.apps.opass.io.details"})
@EnableJpaRepositories(basePackages = {"com.overrideeg.apps.opass.io.repositories"},
        repositoryBaseClass = JpaRepositoryCustomisationsImpl.class)
public class opassApplication extends SpringBootServletInitializer implements WebMvcConfigurer {
    @Autowired
    UsersService usersService;

    public static void main(String[] args) {
        SpringApplication.run(opassApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationFilter(this.usersService));
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(opassApplication.class);
    }


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.getServletRegistration(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
                .setInitParameter("dispatchOptionsRequest", "true");
    }
}
