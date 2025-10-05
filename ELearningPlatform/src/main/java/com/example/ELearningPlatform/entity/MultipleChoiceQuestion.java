package com.example.ELearningPlatform.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Entity
@DiscriminatorValue("MULTIPLE_CHOICE")
@Getter
public class MultipleChoiceQuestion extends Question {
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "mc_options",
            joinColumns = @JoinColumn(name = "question_id")
    )
    @Column(name = "option_values")
    private LinkedList<String> options = new LinkedList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "mc_correct_indexes",
            joinColumns = @JoinColumn(name = "question_id")
    )
    @Column(name = "correct_indexes")
    private List<Integer>  correctIndexes = new LinkedList<>();

    @Override
    public String getType() {
        return "MULTIPLE_CHOICE";
    }
}
