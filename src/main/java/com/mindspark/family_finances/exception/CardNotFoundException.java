package com.mindspark.family_finances.exception;

import jakarta.persistence.EntityNotFoundException;

public class CardNotFoundException extends EntityNotFoundException {
    public CardNotFoundException(String message) {
        super(message);
    }
}
