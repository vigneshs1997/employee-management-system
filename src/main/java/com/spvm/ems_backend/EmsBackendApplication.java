package com.spvm.ems_backend;

import com.spvm.ems_backend.dto.EmployeeDto;
import com.spvm.ems_backend.entity.Address;
import com.spvm.ems_backend.entity.Employee;
import com.spvm.ems_backend.entity.Project;
import com.spvm.ems_backend.entity.Spouse;
import com.spvm.ems_backend.mapper.EmployeeMapper;
import com.spvm.ems_backend.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//includes(@SpringBootConfiguration, @EnableAutoConfiguration, @ComponentScan)
public class EmsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmsBackendApplication.class, args);
	}

    /*@Bean
    * Create this object (CommandLineRunner), manage it as a Spring bean, and make it available for dependency injection.
    * CommandLineRunner which is service comes from 3rd party library. But not yours.
    * We can not see source code of it*/

//	@Bean
//	public CommandLineRunner initialCreate(EmployeeService employeeService){
//        return 	(args) -> {
//			Address address = new Address("Line 1","Line 2","ZipCode","City","State","Country");
//			Project project = new Project("Name", "Client Name");
//			Spouse spouse = new Spouse("Name","Mobile",28L);
//
//			Employee employee = new Employee("firstName","lastName","vigneh@gamil.com");
//			employee.addAddress(address);
//			employee.addProject(project);
//			employee.assignSpouse(spouse);
//
//            employeeService.createEmployee(EmployeeMapper.mapToEmployeeDto(employee));
//
//            System.out.println("Getting an Employee: ");
//            EmployeeDto employee1 =employeeService.getEmployeeById(1L);
//            /*SELECT * FROM employees LEFT JOIN spouse ON spouse.id = employees.foreign_key_spouse WHERE employees.id = 1;
//            * Since Employee has OneToOne relationship with Spouse, Eager loading get acts activated by default. so Details of
//              Employee along with Spouse gets called but the other child like project and address will not get called*/
//		};
//
//	}
}
