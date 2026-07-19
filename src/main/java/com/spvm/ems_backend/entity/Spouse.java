package com.spvm.ems_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "spouse")
@Entity
@Data
public class Spouse {

    /*IDENTITY -> MySQL
    * SEQUENCE -> Postgres and Oracle
    * AUTO -> Hibernate decides(IDENTITY or SEQUENCE) based on database implemented
    * UUID(550e8400-e29b-41d4-a716-446655440000) -> Common for All databases*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mobile;
    private Long age;
    /*1. Bidirectional mapping - employee column will not create in Spouse table
    * 2. Spouse table is parent because it gives its id to employee as a foreign key*/
    @OneToOne(mappedBy = "spouse")
    @JsonBackReference
    private Employee employee;

    public Spouse() {
    }

    public Spouse(Long id, String name, String mobile, Long age, Employee employee) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.age = age;
        this.employee = employee;
    }

    public Spouse(String name, String mobile, Long age) {
        this.name = name;
        this.mobile = mobile;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
