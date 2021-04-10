package org.newton.webshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "questions")
@Entity
public class Question {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, nullable = false)
    private String id;

    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    @Column(length = 50, nullable = false)
    private String questionText;
}