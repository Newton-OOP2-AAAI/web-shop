package org.newton.webshop.models;

import javax.persistence.*;
import java.util.Set;

@Table(name = "customers")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @OneToMany(mappedBy = "customer")
    private Set<Cart> carts;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "streetname")
    private String streetname;

    @Column(name = "streetnumber")
    private Integer streetnumber;

    @Column(name = "zip_code")
    private Integer zip_code;

    @Column(name = "city")
    private String city;

    public Customer() {
    }

    public Customer(Integer id, String firstname, String lastname, String phone, String email, String streetname, Integer streetnumber, Integer zip_code, String city) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.streetname = streetname;
        this.streetnumber = streetnumber;
        this.zip_code = zip_code;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public Integer getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(Integer streetnumber) {
        this.streetnumber = streetnumber;
    }

    public Integer getZip_code() {
        return zip_code;
    }

    public void setZip_code(Integer zip_code) {
        this.zip_code = zip_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
