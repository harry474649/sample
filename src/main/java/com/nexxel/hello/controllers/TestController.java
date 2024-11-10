package com.nexxel.hello.controllers;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import org.bson.Document;

@RestController
public class TestController {

    private final MongoTemplate mongoTemplate;

    public TestController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/test-mongo")
    public String testMongo() {
        System.out.println("Hellooo....");
        try {
            mongoTemplate.getDb().runCommand(new Document("ping", 1));
            return "Connected to MongoDB successfully!";
        } catch (Exception e) {
            return "Connection failed: " + e.getMessage() + "\nStack trace: " + e.getStackTrace()[0];
        }
    }

    @GetMapping("/hello/world")
    public String hello() {
        return "Hello from ECS!";
    }
}