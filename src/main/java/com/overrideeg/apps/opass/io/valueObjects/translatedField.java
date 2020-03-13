package com.overrideeg.apps.opass.io.valueObjects;

import javax.persistence.Embeddable;

@Embeddable
public class translatedField {
    private String ar;
    private String en;

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }
}
