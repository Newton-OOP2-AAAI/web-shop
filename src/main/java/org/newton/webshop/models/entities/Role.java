package org.newton.webshop.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, nullable = false)
    private String id;

    @OneToMany(mappedBy = "role")
    private Set<Employee> employees;

    @Column(nullable = false)
    private Boolean employee;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Boolean chatbot;

    @Column(nullable = false)
    private Boolean categories;

    @Column(nullable = false)
    private Boolean products;
}