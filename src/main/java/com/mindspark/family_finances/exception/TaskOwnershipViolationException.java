package com.mindspark.family_finances.exception;

public class TaskOwnershipViolationException extends RuntimeException {
    public TaskOwnershipViolationException(String message) {
        super(message);
    }
}
