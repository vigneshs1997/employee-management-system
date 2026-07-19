package com.spvm.ems_backend.service.impl;

import com.spvm.ems_backend.dto.EmployeeDto;
import com.spvm.ems_backend.entity.Address;
import com.spvm.ems_backend.entity.Employee;
import com.spvm.ems_backend.entity.Project;
import com.spvm.ems_backend.exception.ResourceNotFoundException;
import com.spvm.ems_backend.mapper.EmployeeMapper;
import com.spvm.ems_backend.repository.EmployeeRepository;
import com.spvm.ems_backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
//@Transactional // It is used to open the session
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

   /*Injecting the dependencies*/
    private EmployeeRepository employeeRepository;

    /*======= Create Employee Manually =======*/
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        /*=====To add value in grand child of child from Parent. But Recursion will occur: =====*/

//        {
//            "timestamp": "2026-07-06T06:15:22.049Z",
//                "status": 500,
//                "error": "Internal Server Error",
//                "trace": "org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.TransientPropertyValueException: Persistent instance of 'com.spvm.ems_backend.entity.Employee'               references an unsaved transient instance of 'com.
//            "message": "org.hibernate.TransientPropertyValueException: Persistent instance of 'com.spvm.ems_backend.entity.Employee' references an unsaved transient instance of              'com.spvm.ems_backend.entity.Project' (persist the transient instance before flushing) [com.spvm.ems_backend.entity.Employee.projects -> com.spvm.ems_backend.entity.Project]",
//                "path": "/api/employees/save"
//        }

            //One-to-One => But Recursion will occur in response
            if(employee.getSpouse() != null){
                  employee.getSpouse().setEmployee(employee);
            }
            //One-to-Many => But Recursion will occur in response
            if(employee.getAddresses() != null){
               for(Address address : employee.getAddresses()){
                   address.setEmployee(employee);
               }
            }
            //Many-to-Many => But Recursion will occur in response
            if(employee.getProjects() != null){
                for(Project project : employee.getProjects()){
                    if(project.getEmployees() == null){
                        project.setEmployees(new ArrayList<>());
                    }
                    project.getEmployees().add(employee);//getEmployee returns list
                }
            }
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto dto = EmployeeMapper.mapToEmployeeDto(savedEmployee);
        return dto;
    }

    /*======= Create Employee Using Helper Methods from Employee Entity =======*/
    public EmployeeDto createEmployeeOption2(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        if (employee.getSpouse() != null) {
            employee.assignSpouse(employee.getSpouse());
        }

        if (employee.getAddresses() != null) {

            List<Address> addresses = new ArrayList<>(employee.getAddresses());
            employee.getAddresses().clear();

            for (Address address : addresses) {
                employee.addAddress(address);
            }
        }

        if (employee.getProjects() != null) {

            List<Project> projects = new ArrayList<>(employee.getProjects());
            employee.getProjects().clear();

            for (Project project : projects) {
                employee.addProject(project);
            }
        }

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    /*======= Create Employee Using Helper Methods and Mapper =======*/
    public EmployeeDto createEmployeeOption3(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployeeOption2(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeById) {
        Employee employee =employeeRepository.findById(employeeById)
                .orElseThrow(()-> //Return Employee Object and accepts Supplier being function interface as a argument(Lambda Expression)
                        new ResourceNotFoundException("Employee is not exist with given id: "+employeeById));//Supplier functional interface
        /*Before @Transactional
          =====================
          LazyInitializationException:
          Cannot lazily initialize collection of role 'com.spvm.ems_backend.entity.Employee.projects' with key '1'
          (no session/session close) => because project is not mentioned or mentioned as a type of (Lazy Loading) Manually*/
        System.out.println("Fetching Projects EmployeeService: ");
        List<Project> projects = employee.getProjects();
        for(Project project :projects){
          System.out.println(project);
        }
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
