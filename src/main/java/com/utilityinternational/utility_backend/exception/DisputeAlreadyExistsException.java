package com.utilityinternational.utility_backend.exception;

public class DisputeAlreadyExistsException extends RuntimeException {

    public DisputeAlreadyExistsException(Long billId) {
        super("A dispute already exists for bill ID: " + billId);
    }
}
