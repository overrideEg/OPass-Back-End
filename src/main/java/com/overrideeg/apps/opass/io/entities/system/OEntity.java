/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.overrideeg.apps.opass.io.entities.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class OEntity implements Serializable {
    protected static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(columnDefinition = "DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3)", insertable = false, updatable = false)
    protected Date createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "DATETIME(3) DEFAULT CURRENT_TIMESTAMP(3)", insertable = false, updatable = false)
    protected Date updatedAt;


    @ManyToOne
    @JoinColumn(name = "creator_id")
    @CreatedBy
    protected User creator;

    @ManyToOne
    @JoinColumn(name = "modifier_id")
    @LastModifiedBy
    protected User modifier;

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    @JsonIgnore
    public boolean isValid () {
        return id != null;
    }

    public Long getId () {
        return id;
    }

    public void setId ( Long id ) {
        this.id = id;
    }

    public Date getCreatedAt () {
        return createdAt;
    }

    public void setCreatedAt ( Date createdAt ) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt () {
        return updatedAt;
    }

    public void setUpdatedAt ( Date updatedAt ) {
        this.updatedAt = updatedAt;
    }

    public User getCreator () {
        return creator;
    }

    public void setCreator ( User creator ) {
        this.creator = creator;
    }

    public User getModifier () {
        return modifier;
    }

    public void setModifier ( User modifier ) {
        this.modifier = modifier;
    }
}
