package com.example.ELearningPlatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("TRUE_FALSE")
@Getter
public class TrueFalseQuestion extends Question {
    @Column(name = "correct")
    private Boolean correct;

    @Override
    public String getType() {
        return "TRUE_FALSE";
    }
}
