package com.utilityinternational.utility_backend.exception;

public class TariffEligibilityException extends RuntimeException {

    public TariffEligibilityException(String reason) {
        super("Tariff change not eligible: " + reason);
    }
}
