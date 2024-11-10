// EmployeeRepository.java
package com.nexxel.hello.repository;

import com.nexxel.hello.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
}