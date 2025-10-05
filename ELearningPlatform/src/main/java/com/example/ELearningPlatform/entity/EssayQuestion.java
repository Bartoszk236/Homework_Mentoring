package com.example.ELearningPlatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("ESSAY")
@Getter
public class EssayQuestion extends Question {
    @Column(name = "max_chars")
    private Integer maxChars;

    @Override
    public String getType() {
        return "ESSAY";
    }
}
