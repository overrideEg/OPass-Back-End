package com.overrideeg.apps.opass.io.valueObjects;

import javax.persistence.Embeddable;

/**
 * translate embeddable object
 * here 3 languages and can add more
 * arabic , english and turkish
 */
@Embeddable
public class translatedField {
    private String ar;
    private String en;
    private String tr;


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

    public String getTr() {
        return tr;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }
}
