package com.example.ELearningPlatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("ESSAY")
@Getter
public class EssayAnswer extends Answer {
    @Column(name = "text")
    private String text;

    @Override
    public String getType() {
        return "ESSAY";
    }
}
