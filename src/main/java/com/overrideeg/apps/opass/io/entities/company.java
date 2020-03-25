package com.overrideeg.apps.opass.io.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;

import javax.persistence.*;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "name.ar", column = @Column(name = "name_ar")),
        @AttributeOverride(name = "name.en", column = @Column(name = "name_en")),
        @AttributeOverride(name = "name.tr", column = @Column(name = "name_tr")),
        @AttributeOverride(name = "description.ar", column = @Column(name = "description_ar")),
        @AttributeOverride(name = "description.en", column = @Column(name = "description_en")),
        @AttributeOverride(name = "description.tr", column = @Column(name = "description_tr")),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class company extends OEntity {
    @Embedded
    @JsonProperty(required = true)
    private translatedField name;
    @Embedded
    @JsonProperty(required = true)
    private translatedField description;
    private String website;
    private String phoneNumber;
    @ManyToOne
    private country country;
    private String database_name;
    private Boolean enabled;

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    public translatedField getName() {
        return name;
    }

    public void setName(translatedField name) {
        this.name = name;
    }

    public translatedField getDescription() {
        return description;
    }

    public void setDescription(translatedField description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public com.overrideeg.apps.opass.io.entities.country getCountry() {
        return country;
    }

    public void setCountry(com.overrideeg.apps.opass.io.entities.country country) {
        this.country = country;
    }

    public String getDatabase_name() {
        return database_name;
    }

    public void setDatabase_name(String database_name) {
        this.database_name = database_name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
