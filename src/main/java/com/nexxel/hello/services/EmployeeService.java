package com.nexxel.hello.services;

import com.nexxel.hello.model.Employee;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final MongoTemplate mongoTemplate;

    public EmployeeService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Employee createEmployee(Employee employee) {
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        return mongoTemplate.save(employee);
    }

    public List<Employee> getAllEmployees(Boolean isActive, String role) {
        Query query = new Query();
        
        if (isActive != null) {
            query.addCriteria(Criteria.where("isActive").is(isActive));
        }
        
        if (role != null && !role.isEmpty()) {
            query.addCriteria(Criteria.where("role").is(role));
        }
        
        query.addCriteria(Criteria.where("isDeleted").is(false));
        return mongoTemplate.find(query, Employee.class);
    }

    public Optional<Employee> getEmployeeById(String id) {
        Query query = new Query(Criteria.where("_id").is(id)
                .and("isDeleted").is(false));
        return Optional.ofNullable(mongoTemplate.findOne(query, Employee.class));
    }

    public Optional<Employee> updateEmployee(String id, Employee employee) {
        Query query = new Query(Criteria.where("_id").is(id)
                .and("isDeleted").is(false));
        
        Update update = new Update()
                .set("firstName", employee.getFirstName())
                .set("lastName", employee.getLastName())
                .set("email", employee.getEmail())
                .set("phoneNumber", employee.getPhoneNumber())
                .set("role", employee.getRole())
                .set("isActive", employee.isActive())
                .set("updatedAt", LocalDateTime.now());

        return Optional.ofNullable(
                mongoTemplate.findAndModify(query, update, Employee.class));
    }

    public void deleteEmployee(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update()
                .set("isDeleted", true)
                .set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, Employee.class);
    }

    public List<Employee> searchEmployees(String email, String firstName, String lastName) {
        Query query = new Query();
        
        if (email != null && !email.isEmpty()) {
            query.addCriteria(Criteria.where("email").regex(email, "i"));
        }
        
        if (firstName != null && !firstName.isEmpty()) {
            query.addCriteria(Criteria.where("firstName").regex(firstName, "i"));
        }
        
        if (lastName != null && !lastName.isEmpty()) {
            query.addCriteria(Criteria.where("lastName").regex(lastName, "i"));
        }
        
        query.addCriteria(Criteria.where("isDeleted").is(false));
        return mongoTemplate.find(query, Employee.class);
    }
}