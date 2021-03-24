package org.newton.webshop.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "answer")
    private Set<Question> questions;

    @Column(length = 500, nullable = false)
    private String text;

    @Column(length = 50, nullable = false)
    private String description;
}