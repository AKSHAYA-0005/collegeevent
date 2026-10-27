package com.college.eventregistration.exception;

/**
 * Exception thrown when a student tries to register twice for the same event with the same email.
 */
public class DuplicateRegistrationException extends RuntimeException {

    public DuplicateRegistrationException(String message) {
        super(message);
    }
}
