package com.utilityinternational.utility_backend.exception;

public class DisputeAlreadyExistsException extends RuntimeException {

    public DisputeAlreadyExistsException(String billNumber) {
        super("An active dispute already exists for bill: " + billNumber);
    }
}
