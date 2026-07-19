package com.spvm.ems_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id", nullable = false, unique = true)
    private String email;

    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /*There are spouse, addresses and projects dependencies (children) for the Employee*/

    /*
    * 1.Since spouse is parent, parent table can not be deleted first because employee has FK of spouse
    *   So delete employee table first then delete spouse table
    * 2.Cascading is used to perform operations(CRUD) from the parent entity to child entity
    *   For example: If I need to save employee where spouse dependency exists, spouse also gets saved in spouse table
    *                If I need to update employee where spouse dependency exists, spouse also gets updated in spouse table
    *                If I need to delete employee where spouse dependency exists, spouse also gets deleted in spouse table
    * 3.In cascading, the spouse does not need any repo layer for CRUD operation
    * 4.Since OneToOne Mapping is associated with Eager Loading by default, spouse only gets called on the query like
        SELECT * FROM employees LEFT JOIN spouse ON spouse.id = employees.foreign_key_spouse WHERE employees.id = 1*/
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)//PERSIST(Save),MERGE(Update),REMOVE(Delete) and REFRESH(Reload)
    @JoinColumn(name = "foreign_key_spouse")
    @JsonManagedReference
    private Spouse spouse;
    /*
    * 1. Since a employee has multiple addresses, handle it in address side with mappedBy
    * 2. To avoid creating extra employees_addresses table, mappedBy(handle in address side) then address will be a child
    *    so delete address(Child) first then delete employee(Parent)  If we need
    * 3. Cascading is used to perform operations(CRUD) from the parent entity to child entity
    *    For example: If I need to save employee where spouse dependency exists, address also gets saved in spouse table
    *                 If I need to update employee where spouse dependency exists, address also gets updated in spouse table
    *                 If I need to delete employee where spouse dependency exists, address also gets deleted in spouse table
    * 4. In cascading, the address does not need any repo layer*/
    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    @JsonIgnore//Instead of (@JsonManagedReference and @JsonBackReference in other side)
    private List<Address> addresses = new ArrayList<>(); //To avoid NullPointerException while executing
    /*1. Cascading is used to perform operations(CRUD) from the parent entity to child entity
    *    For example: If I need to save employee where spouse dependency exists, address also gets saved in spouse table
    *                 If I need to update employee where spouse dependency exists, address also gets updated in spouse table
    *                 If I need to delete employee where spouse dependency exists, address also gets deleted in spouse table
    * 2. In cascading, the address does not need any repo layer*/
    @ManyToMany(cascade = {CascadeType.PERSIST,
//            CascadeType.REMOVE, => because projects are often shared between multiple employees,
//            and cascading removes can delete shared entities unexpectedly.
            CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(name = "employee_project", //(the default employees_projects => employee_project)
            joinColumns = @JoinColumn(name = "fk_employee"),//the name in newly created table employee_project
            inverseJoinColumns = @JoinColumn(name = "fk_project"))//the name in newly created table employee_project
    @JsonIgnore//Instead of (@JsonManagedReference and @JsonBackReference in other side)
    private List<Project> projects = new ArrayList<>();

    /*
    * The below two methods are helper methods. They are not required by Hibernate,
      but they help you keep both sides of a bidirectional relationship synchronized.
    */


    // =====================================================
    // Helper Methods
    // =====================================================

    /**
     * Assign Spouse
     */
    public void assignSpouse(Spouse spouse) {

        this.spouse = spouse;// current object's spouse = spouse

        if (spouse != null) {
            spouse.setEmployee(this);// spouse.setEmployee(current object)
        }
    }

    /**
     * Remove Spouse
     */
    public void removeSpouse() {

        if (this.spouse != null) {
            this.spouse.setEmployee(null);
        }

        this.spouse = null;
    }

    /**
     * Add Address
     */
    public void addAddress(Address address) {

        if (address == null) {
            return;
        }

        addresses.add(address);
        address.setEmployee(this);
    }

    /**
     * Remove Address
     */
    public void removeAddress(Address address) {

        if (address == null) {
            return;
        }

        addresses.remove(address);
        address.setEmployee(null);
    }

    /**
     * Add Project
     */
    public void addProject(Project project) {

        if (project == null) {
            return;
        }

        if (!projects.contains(project)) {
            projects.add(project);
        }

        if (project.getEmployees() == null) {
            project.setEmployees(new ArrayList<>());
        }

        if (!project.getEmployees().contains(this)) {
            project.getEmployees().add(this);
        }
    }

    /**
     * Remove Project
     */
    public void removeProject(Project project) {

        if (project == null) {
            return;
        }

        projects.remove(project);

        if (project.getEmployees() != null) {
            project.getEmployees().remove(this);
        }
    }

}
