package com.spvm.ems_backend.service;

import com.spvm.ems_backend.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
     EmployeeDto createEmployee(EmployeeDto employeeDto);
     EmployeeDto getEmployeeById(Long employeeById);
     List<EmployeeDto> getAllEmployees();
     EmployeeDto updateEmployeeById(Long employeeById, EmployeeDto updatedEmployeeDto);
     void deleteEmployeeById(Long employeeById);
}
