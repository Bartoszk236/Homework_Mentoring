package com.example.ELearningPlatform.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@DiscriminatorValue("MULTIPLE_CHOICE")
@Getter
public class MultipleChoiceAnswer extends Answer {
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "ma_answers",
            joinColumns = @JoinColumn(name = "answer_id")
    )
    @Column(name = "choice_indexes")
    private List<Integer> choiceIndexes;

    @Override
    public String getType() {
        return "MULTIPLE_ANSWER";
    }
}
