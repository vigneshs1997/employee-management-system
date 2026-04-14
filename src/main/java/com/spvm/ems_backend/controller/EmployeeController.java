package com.spvm.ems_backend.controller;
import com.spvm.ems_backend.dto.EmployeeDto;
import com.spvm.ems_backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){//JSON Object from http request -> EmployeeDto
         EmployeeDto savedDto =  employeeService.createEmployee(employeeDto);
         return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId){// Path variable -> Java variable
              EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
              return ResponseEntity.ok(employeeDto);
    }
    @GetMapping("/all-employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> allEmployees = employeeService.getAllEmployees();
        return ResponseEntity.ok(allEmployees);
    }

    @PutMapping("updateEmployee/{id}")
    public ResponseEntity<EmployeeDto> updateEmployeeById(@PathVariable("id") Long employeeById,@RequestBody EmployeeDto updatedEmployeeDto){
       EmployeeDto employee = employeeService.updateEmployeeById(employeeById,updatedEmployeeDto);
       return ResponseEntity.ok(employee);
    }

    @DeleteMapping("deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long employeeById){//Path variable -> Java variable
        employeeService.deleteEmployeeById(employeeById);
        return ResponseEntity.ok("Employee deleted successfully with id :"+employeeById);
    };
}
