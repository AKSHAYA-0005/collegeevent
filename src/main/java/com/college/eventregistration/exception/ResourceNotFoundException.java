package com.college.eventregistration.exception;

/**
 * Exception thrown when a requested resource (Event, Registration) does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
