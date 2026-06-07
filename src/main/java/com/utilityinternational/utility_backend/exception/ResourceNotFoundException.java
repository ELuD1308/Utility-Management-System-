package com.utilityinternational.utility_backend.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String field, Object value) {
        super(resourceName + " not found with " + field + ": " + value);
    }
}
