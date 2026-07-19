package com.spvm.ems_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data//@Getter,@Setter and @Constructor
@Table(name = "address")
@Entity
public class Address {
    /*IDENTITY -> MySQL
     * SEQUENCE -> Postgres and Oracle
     * AUTO -> Hibernate decides(IDENTITY or SEQUENCE) based on database implemented
     * UUID(550e8400-e29b-41d4-a716-446655440000) -> Common for All databases*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String line1;
    private String line2;
    private String pinCode;
    private String city;
    private String state;
    private String country;
    /*
    *1. mappedBy is not allowed on @ManyToOne because duplicate id will not be allowed on @OneToMany
    *2. mappedBy is used only on the inverse side of a bidirectional relationship ex:@OneToMany,@OneToOne and @ManyToMany
    */
    @ManyToOne
    private Employee employee;

    public Address(String line1, String line2, String pinCode, String city, String state, String country) {
        this.line1 = line1;
        this.line2 = line2;
        this.pinCode = pinCode;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public Address() {

    }
}
