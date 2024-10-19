package com.mindspark.family_finances.exception.task;

public class TaskOwnershipViolationException extends RuntimeException {
    public TaskOwnershipViolationException(String message) {
        super(message);
    }
}
