package com.spvm.ems_backend.repository;

import com.spvm.ems_backend.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository - SimpleRepo which is a child class is already annotated
//@Transactional - SimpleRepo which is a child class is already annotated
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
