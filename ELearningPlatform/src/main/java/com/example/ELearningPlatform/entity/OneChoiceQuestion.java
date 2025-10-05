package com.example.ELearningPlatform.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.LinkedList;

@Entity
@DiscriminatorValue("ONE_CHOICE")
@Getter
public class OneChoiceQuestion extends Question {
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "oc_options",
            joinColumns = @JoinColumn(name = "question_id")
    )
    @Column(name = "option_values")
    private LinkedList<String> options = new LinkedList<>();

    @Column(name = "correct_index")
    private Integer correctIndex;

    @Override
    public String getType() {
        return "ONE_CHOICE";
    }
}
