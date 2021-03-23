package org.newton.webshop.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.newton.webshop.models.entities.Account;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@Table(name = "customers")
@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "customer_id", length = 50, nullable = false)
    private String id;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

    @OneToOne(mappedBy = "customer")
    private Account account;

    @Column(length = 50, nullable = false)
    private String firstname;

    @Column(length = 50, nullable = false)
    private String lastname;

    @Column(length = 50, nullable = false)
    private String phone;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String streetname;

    @Column(length = 50, nullable = false)
    private Integer streetnumber;

    @Column(length = 50, nullable = false, name = "zip_code")
    private Integer zipCode;

    @Column(length = 50, nullable = false)
    private String city;

}
