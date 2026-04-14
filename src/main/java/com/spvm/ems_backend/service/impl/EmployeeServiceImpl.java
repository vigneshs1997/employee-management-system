package com.spvm.ems_backend.service.impl;

import com.spvm.ems_backend.dto.EmployeeDto;
import com.spvm.ems_backend.entity.Employee;
import com.spvm.ems_backend.exception.ResourceNotFoundException;
import com.spvm.ems_backend.mapper.EmployeeMapper;
import com.spvm.ems_backend.repository.EmployeeRepository;
import com.spvm.ems_backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
   /*Injecting the dependencies*/
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto dto = EmployeeMapper.mapToEmployeeDto(savedEmployee);
        return dto;
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeById) {
        Employee employee =employeeRepository.findById(employeeById)
                .orElseThrow(()-> //Return Employee Object and accepts Supplier being function interface as a argument(Lambda Expression)
                        new ResourceNotFoundException("Employee is not exist with given id: "+employeeById));//Supplier functional interface
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();
        for(Employee employee :employees){
            EmployeeMapper.mapToEmployeeDto(employee);
        }
        //Employees => Single Employee => Employee DTOs => List of Employee DTOs
        return employees.stream().map((employee)->EmployeeMapper.mapToEmployeeDto(employee)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployeeById(Long employeeById,EmployeeDto updatedEmployeeDto) {
           Employee employee = employeeRepository.findById(employeeById)
                   .orElseThrow(()->
                           new ResourceNotFoundException("Employee does not exist with given id: "+employeeById));
           employee.setId(employeeById);
           employee.setFirstName(updatedEmployeeDto.getFirstName());
           employee.setLastName(updatedEmployeeDto.getLastName());
           employee.setEmail(updatedEmployeeDto.getEmail());
           employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public void deleteEmployeeById(Long employeeById) {
        Employee employee  = employeeRepository.findById(employeeById)
                .orElseThrow(()->
                        new ResourceNotFoundException("Employee does not exist with given id: "+employeeById));
        employeeRepository.deleteById(employeeById);
    }
}
