/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities.reports;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Map;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "name.ar", column = @Column(name = "name_ar")),
        @AttributeOverride(name = "name.en", column = @Column(name = "name_en")),
        @AttributeOverride(name = "name.tr", column = @Column(name = "name_tr")),
})
public class reportDefinition extends OEntity {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String filePath;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long size;
    @Embedded
    private translatedField name;
    private String fileName;
    @JsonIgnore
    private String jasperFileName;
    @ManyToOne(fetch = FetchType.EAGER)
    private reportCategory reportCategory;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "parameter_name")
    @Column(name = "parameter_type")
    @Fetch(FetchMode.SUBSELECT)
    private Map<String, String> params;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public translatedField getName() {
        return name;
    }

    public void setName(translatedField name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getJasperFileName() {
        return jasperFileName;
    }

    public void setJasperFileName(String jasperFileName) {
        this.jasperFileName = jasperFileName;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public com.overrideeg.apps.opass.io.entities.reports.reportCategory getReportCategory() {
        return reportCategory;
    }

    public void setReportCategory(com.overrideeg.apps.opass.io.entities.reports.reportCategory reportCategory) {
        this.reportCategory = reportCategory;
    }
}
