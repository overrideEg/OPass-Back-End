package com.overrideeg.apps.opass.io.entity.Admin.BasicData.Address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.overrideeg.apps.opass.annotations.Local;
import com.overrideeg.apps.opass.io.entity.System.Localized;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class cityLocalized {
    @EmbeddedId
    @JsonIgnore
    private Localized localized;
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    @Local
    @JsonIgnore
    private city city;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Localized getLocalized() {
        return localized;
    }

    public void setLocalized(Localized localized) {
        this.localized = localized;
    }

    public com.overrideeg.apps.opass.io.entity.Admin.BasicData.Address.city getCity() {
        return city;
    }

    public void setCity(city city) {
        this.city = city;
    }
}
