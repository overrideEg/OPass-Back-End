package com.overrideeg.apps.opass.io.entities.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.overrideeg.apps.opass.enums.userType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users extends OEntity {
    @Column(nullable = false, unique = true, length = 15)
    private String mobile;
    private String email;
    @Column(nullable = false, length = 16)
    private String password;
    @Enumerated(EnumType.STRING)
    private userType userType;


}
