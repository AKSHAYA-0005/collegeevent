package com.college.eventregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * College Event Registration System - Spring Boot Application Entry Point
 * III B.Tech Lab External Exam - Full Stack Development
 */
@SpringBootApplication
public class EventRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventRegistrationApplication.class, args);
        System.out.println("==========================================================");
        System.out.println(" College Event Registration System Backend Started Successfully!");
        System.out.println(" Server running on: http://localhost:8080");
        System.out.println(" REST API Base URL: http://localhost:8080/api");
        System.out.println("==========================================================");
    }
}
