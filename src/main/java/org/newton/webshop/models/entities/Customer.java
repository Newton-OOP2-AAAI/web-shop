package org.newton.webshop.models.entities;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.newton.webshop.models.dto.creation.AccountCreationDto;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

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

    /**
     * Utility method that establishes sets the bidirectional relation between an Account instance
     * Method should only be used to avoid having to use respective setters twice.
     * Method does not check for existing associations on either side and will simply overwrite existing associations.
     * Keep in mind that either the invoking Customer entity or the Account entity should be persisted in the database,
     * otherwise neither can be persisted after this method is called.
     *
     * @param account Account instance.
     */
    public void setAccountAssociation(Account account) {
        this.account = account;
        account.setCustomer(this);
    }
}
