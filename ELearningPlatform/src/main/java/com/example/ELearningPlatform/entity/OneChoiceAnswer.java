package com.example.ELearningPlatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("ONE_CHOICE")
@Getter
public class OneChoiceAnswer extends Answer {
    @Column(name = "choice_index")
    private Integer choiceIndex;

    @Override
    public String getType() {
        return "ONE_ANSWER";
    }
}
