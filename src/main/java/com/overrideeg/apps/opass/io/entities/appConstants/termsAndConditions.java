/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities.appConstants;

import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;

import javax.persistence.*;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "title.ar", column = @Column(name = "title_ar")),
        @AttributeOverride(name = "title.en", column = @Column(name = "title_en")),
        @AttributeOverride(name = "title.tr", column = @Column(name = "title_tr")),
        @AttributeOverride(name = "body.ar", column = @Column(name = "body_ar")),
        @AttributeOverride(name = "body.en", column = @Column(name = "body_en")),
        @AttributeOverride(name = "body.tr", column = @Column(name = "body_tr")),
})
public class termsAndConditions extends OEntity {
    @Embedded
    private translatedField title;
    @Embedded
    private translatedField body;

    public translatedField getTitle() {
        return title;
    }

    public void setTitle(translatedField title) {
        this.title = title;
    }

    public translatedField getBody() {
        return body;
    }

    public void setBody(translatedField body) {
        this.body = body;
    }
}
