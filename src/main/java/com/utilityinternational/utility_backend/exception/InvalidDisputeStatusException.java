package com.utilityinternational.utility_backend.exception;

public class InvalidDisputeStatusException extends RuntimeException {

    public InvalidDisputeStatusException(String currentStatus, String targetStatus) {
        super(String.format("Cannot transition dispute from '%s' to '%s'", currentStatus, targetStatus));
    }
}
