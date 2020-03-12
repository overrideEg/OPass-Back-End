package com.overrideeg.apps.opass.io.entity.Admin.BasicData.Address;


import com.fasterxml.jackson.annotation.*;
import com.overrideeg.apps.opass.annotations.Forign;
import com.overrideeg.apps.opass.annotations.Local;
import com.overrideeg.apps.opass.io.entity.System.OEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class city extends OEntity {

//    @ManyToOne(fetch = FetchType.LAZY)
//    private country country;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Forign
    private String country_id;
    @Transient
    @Local
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String name;
    private Double price;
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "localized.locale")
    private Map<String, cityLocalized> localizations = new HashMap<>();

    public String getName(String locale) {
        return localizations.get(locale).getName();
    }

//    public com.overrideeg.apps.ocommerce.io.entity.Admin.BasicData.Address.country getCountry() {
//        return country;
//    }
//
//    public void setCountry(com.overrideeg.apps.ocommerce.io.entity.Admin.BasicData.Address.country country) {
//        this.country = country;
//    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Map<String, cityLocalized> getLocalizations() {
        return localizations;
    }

    public void setLocalizations(Map<String, cityLocalized> localizations) {
        this.localizations = localizations;
    }
}
