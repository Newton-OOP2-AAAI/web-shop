package org.newton.webshop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;


@NoArgsConstructor
@Setter
@Getter
@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50, nullable = false)
    private String id;

    @OneToMany(mappedBy = "role")
    private Set<Employee> employees;

    @Column(nullable = false)
    private Boolean employee;

    @Column(nullable = false)
    private Boolean chatbot;

    @Column(nullable = false)
    private Boolean categories;

    @Column(nullable = false)
    private Boolean products;

}