package com.spvm.ems_backend.dto;

import com.spvm.ems_backend.entity.Address;
import com.spvm.ems_backend.entity.Project;
import com.spvm.ems_backend.entity.Spouse;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Spouse spouse;
    private List<Address> addresses;
    private List<Project> projects;
}
