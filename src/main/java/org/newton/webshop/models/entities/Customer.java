package org.newton.webshop.models.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.newton.webshop.models.dto.creation.AccountCreationDto;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@Table(name = "customers")
@Entity
public class Customer {

    public Customer(AccountCreationDto accountCreationDto) {
        this.firstname = accountCreationDto.getFirstname();
        this.lastname = accountCreationDto.getLastname();
        this.phone = accountCreationDto.getPhone();
        this.email = accountCreationDto.getEmail();
        this.address = new Address(accountCreationDto);
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, nullable = false)
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

    @Embedded
    private Address address;
}
