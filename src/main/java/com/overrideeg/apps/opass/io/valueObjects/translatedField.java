package com.overrideeg.apps.opass.io.valueObjects;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;

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

    public translatedField(String ar,
                           String en,
                           String tr) {
        this.ar = ar;
        this.en = en;
        this.tr = tr;
    }

    public translatedField() {
    }

    @PrePersist()
    public void PrePersist() {
        if ((getAr() == null || getAr().equals("")) && getEn() != null)
            setAr(getEn());
        if ((getTr() == null || getTr().equals("")) && getEn() != null)
            setTr(getEn());
        if ((getEn() == null || getEn().equals("")) && getAr() != null)
            setEn(getAr());
        if ((getEn() == null || getEn().equals("")) && getTr() != null)
            setEn(getTr());
        if ((getTr() == null || getTr().equals("")) && getEn() != null)
            setTr(getEn());
        if ((getTr() == null || getTr().equals("")) && getAr() != null)
            setEn(getAr());
    }

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
