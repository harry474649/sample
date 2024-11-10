package com.nexxel.hello.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "employees")
public class Employee {
    @Id
    private String id;

    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String phoneNumber;
    private String role;
    private boolean isActive = true;
    private boolean isDeleted = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
    // Add all necessary getters and setters here
}