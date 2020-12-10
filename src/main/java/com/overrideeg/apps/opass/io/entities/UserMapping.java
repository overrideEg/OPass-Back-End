package com.overrideeg.apps.opass.io.entities;


import com.overrideeg.apps.opass.io.entities.system.OEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.io.Serializable;

@Entity
public class UserMapping extends OEntity implements Serializable {


    @Column(unique = true)
    private String username;
    private Long companyId;

    @PrePersist
    public void PrePersist () {
        if (companyId == null)
            companyId = 0L;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername ( String username ) {
        this.username = username;
    }

    public Long getCompanyId () {
        return companyId;
    }

    public void setCompanyId ( Long companyId ) {
        this.companyId = companyId;
    }
}
