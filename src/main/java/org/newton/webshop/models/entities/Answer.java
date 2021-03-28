package org.newton.webshop.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.newton.webshop.models.dto.creation.AnswerCreationDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "answers")
@Entity
public class Answer {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, nullable = false)
    private String id;

    @OneToMany(mappedBy = "answer",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Question> questions;

    @Column(name = "answer_text", length = 500, nullable = false)
    private String answerText;

    @Column(length = 50, nullable = false)
    private String description;

    public Answer(AnswerCreationDto creationDto) {
        this.questions = new HashSet<>();
        this.answerText = creationDto.getAnswerText();
        this.description = creationDto.getDescription();
    }

    public void addQuestion(Question question) {
        this.getQuestions().add(question);
        question.setAnswer(this);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }
}