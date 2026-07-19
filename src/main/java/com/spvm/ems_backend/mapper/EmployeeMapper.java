package com.spvm.ems_backend.mapper;

import com.spvm.ems_backend.dto.EmployeeDto;
import com.spvm.ems_backend.entity.Address;
import com.spvm.ems_backend.entity.Employee;
import com.spvm.ems_backend.entity.Project;

public class EmployeeMapper {
                   /*Converting Employee to EmployeeDto*/
    public static EmployeeDto mapToEmployeeDto(Employee employee){
          return new EmployeeDto( //Constructor is created in the EmployeeDto class
                  employee.getId(),
                  employee.getFirstName(),
                  employee.getLastName(),
                  employee.getEmail(),
                  employee.getSpouse(),
                  employee.getAddresses(),
                  employee.getProjects()
          );
    }
                  /*Converting EmployeeDto to Employee*/
    public static Employee mapToEmployee(EmployeeDto employeeDto){
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getSpouse(),
                employeeDto.getAddresses(),
                employeeDto.getProjects()
        );
    }

    public static Employee mapToEmployeeOption2(EmployeeDto employeeDto) {

        Employee employee = new Employee();

        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());

        // One-to-One
        if (employeeDto.getSpouse() != null) {
            employee.assignSpouse(employeeDto.getSpouse());
        }

        // One-to-Many
        if (employeeDto.getAddresses() != null) {
            for (Address address : employeeDto.getAddresses()) {
                employee.addAddress(address);
            }
        }

        // Many-to-Many
        if (employeeDto.getProjects() != null) {
            for (Project project : employeeDto.getProjects()) {
                employee.addProject(project);
            }
        }

        return employee;
    }
}
