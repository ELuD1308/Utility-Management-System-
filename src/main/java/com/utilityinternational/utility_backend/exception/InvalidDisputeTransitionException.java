package com.utilityinternational.utility_backend.exception;

import com.utilityinternational.utility_backend.enums.DisputeStatus;

public class InvalidDisputeTransitionException extends RuntimeException {

    public InvalidDisputeTransitionException(DisputeStatus from, DisputeStatus to) {
        super("Cannot transition dispute from " + from + " to " + to);
    }
}
