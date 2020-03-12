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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Forign
    private String country_id;
    @Local
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String name;
    private Double price;

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
}
