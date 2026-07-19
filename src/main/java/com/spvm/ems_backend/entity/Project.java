   package com.spvm.ems_backend.entity;
   
   import jakarta.persistence.*;
   import lombok.Data;
   
   import java.util.List;
   
   @Table(name = "project")
   @Data//@Getter + @Setter + @AllArgConstructor + @NoAllArgConstructor
   @Entity
   public class Project {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String name;
      private String clientName;
       /*
        * 1. To avoid creating extra employees_projects column, mappedBy(handle in employee side) then employee will be a child
        * 2. Delete employee(child) first then delete project(Parent) If we need*/
      @ManyToMany(mappedBy = "projects")
      private List<Employee> employees;

      public Project(String name, String clientName) {
         this.name = name;
         this.clientName = clientName;
      }

      public Project() {
      }
   }
