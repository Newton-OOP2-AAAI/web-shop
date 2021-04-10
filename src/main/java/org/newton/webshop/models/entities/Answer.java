package org.newton.webshop.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    /**
     * Utility method that sets a bidirectional association.
     * Use this method for setting associations between two entities. That's what the method was written for.
     * Don't use this method for changing associations. If this use case comes up in the future, the method should be rewritten.
     *
     * @param question question entity to be associated with
     */
    public void addQuestion(Question question) {
        this.questions.add(question);
        question.setAnswer(this);
    }
}