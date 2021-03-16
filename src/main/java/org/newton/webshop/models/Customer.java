package org.newton.webshop.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
@Setter
@Getter
@NoArgsConstructor

@Table(name = "customers")
@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy="org.hibernate.id.UUIDGenerator")
    @Column(name = "customer_id", length= 50, nullable = false)
    private String id;

    @OneToMany(mappedBy = "customer")
    private Set<Cart> carts;

    @OneToOne(mappedBy = "customer")
    private Account account;

    @Column(length=50,nullable = false)
    private String firstname;

    @Column(length=50,nullable = false)
    private String lastname;

    @Column(length=50)
    private String phone;

    @Column(length=50,nullable = false)
    private String email;

    @Column(length=50,nullable = false)
    private String streetname;

    @Column(length=50,nullable = false)
    private Integer streetnumber;

    @Column(length=50,nullable = false,name = "zip_code")
    private Integer zipCode;

    @Column(length=50,nullable = false)
    private String city;

}
