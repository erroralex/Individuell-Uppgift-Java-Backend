package com.notnilsson.individuelluppgiftjavabackend.entity;

import jakarta.persistence.*;

/**
 * <h2>Address</h2>
 * <p>Entitetsklass som representerar en adress i databasen.</p>
 */
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street", nullable = false, length = 50)
    private String street;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    protected Address() {

    }

    public Address(String street, String city, String postalCode) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
