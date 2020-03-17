package com.overrideeg.apps.opass.io.entities.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class OEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "Africa/Cairo")
    private Date lastUpdateDate;

//    @Version
//    private int version;

    @PrePersist
    public void PrePersist() {
        creationDate = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    public void PreUpdate() {
        lastUpdateDate = new Date(System.currentTimeMillis());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


//    public int getVersion() {
//        return version;
//    }
//
//    public void setVersion(int version) {
//        this.version = version;
//    }
}
