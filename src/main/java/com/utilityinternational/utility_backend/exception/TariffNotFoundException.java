package com.utilityinternational.utility_backend.exception;

public class TariffNotFoundException extends RuntimeException {

    public TariffNotFoundException(Long tariffId) {
        super("Tariff not found with id: " + tariffId);
    }
}
