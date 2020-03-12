package com.overrideeg.apps.opass.io.entity.System;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Localized id.
 */
@Embeddable
public class Localized  implements Serializable {

    private static final long serialVersionUID = 1089196571270403924L;

    private Long id;

    private String locale;


    public Localized() {
    }

    public Localized(String locale) {
        this.locale = locale;
    }

    /**
     * Instantiates a new Localized id.
     */

    // getter and setter methods ...




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}