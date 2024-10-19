package com.mindspark.family_finances.exception.payment;

public class ReceiverNotFoundException extends RuntimeException {
    public ReceiverNotFoundException (String message) {
        super(message);
    }

}
