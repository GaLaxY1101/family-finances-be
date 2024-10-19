package com.mindspark.family_finances.exception;

import jakarta.persistence.EntityNotFoundException;

public class BankAccountDoesNotExistException extends EntityNotFoundException {
    public BankAccountDoesNotExistException(String message) {
        super(message);
    }
}
