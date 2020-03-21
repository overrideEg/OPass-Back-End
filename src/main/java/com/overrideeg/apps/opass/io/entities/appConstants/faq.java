/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.io.entities.appConstants;

import com.overrideeg.apps.opass.io.entities.system.OEntity;
import com.overrideeg.apps.opass.io.valueObjects.translatedField;

import javax.persistence.*;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "question.ar", column = @Column(name = "question_ar")),
        @AttributeOverride(name = "question.en", column = @Column(name = "question_en")),
        @AttributeOverride(name = "question.tr", column = @Column(name = "question_tr")),
        @AttributeOverride(name = "answer.ar", column = @Column(name = "answer_ar")),
        @AttributeOverride(name = "answer.en", column = @Column(name = "answer_en")),
        @AttributeOverride(name = "answer.tr", column = @Column(name = "answer_tr")),
})
public class faq extends OEntity {
    @Embedded
    private translatedField question;
    @Embedded
    private translatedField answer;

    public translatedField getQuestion() {
        return question;
    }

    public void setQuestion(translatedField question) {
        this.question = question;
    }

    public translatedField getAnswer() {
        return answer;
    }

    public void setAnswer(translatedField answer) {
        this.answer = answer;
    }
}
