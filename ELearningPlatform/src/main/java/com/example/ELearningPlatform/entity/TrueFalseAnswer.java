package com.example.ELearningPlatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("TRUE_FALSE")
@Getter
public class TrueFalseAnswer extends Answer {
    @Column(name = "answer_value")
    private Boolean answerValue;

    @Override
    public String getType() {
        return "TRUE_FALSE";
    }
}
