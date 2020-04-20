/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities.appConstants;

import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;
import com.overrideeg.apps.opass.io.valueObjects.translatedLob;

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
    private translatedLob body;

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    public translatedField getTitle() {
        return title;
    }

    public void setTitle(translatedField title) {
        this.title = title;
    }

    public translatedLob getBody () {
        return body;
    }

    public void setBody ( translatedLob body ) {
        this.body = body;
    }
}
