package com.utilityinternational.utility_backend.util;

import com.utilityinternational.utility_backend.enums.DisputeStatus;
import com.utilityinternational.utility_backend.exception.InvalidDisputeStatusException;

import java.util.Map;
import java.util.Set;

public final class DisputeStatusValidator {

    private DisputeStatusValidator() {}

    private static final Map<DisputeStatus, Set<DisputeStatus>> TRANSITIONS = Map.of(
        DisputeStatus.PENDING,      Set.of(DisputeStatus.UNDER_REVIEW, DisputeStatus.REJECTED),
        DisputeStatus.UNDER_REVIEW, Set.of(DisputeStatus.RESOLVED, DisputeStatus.REJECTED),
        DisputeStatus.RESOLVED,     Set.of(),
        DisputeStatus.REJECTED,     Set.of()
    );

    public static void validate(DisputeStatus current, DisputeStatus next) {
        Set<DisputeStatus> allowed = TRANSITIONS.getOrDefault(current, Set.of());
        if (!allowed.contains(next)) {
            throw new InvalidDisputeStatusException(current.name(), next.name());
        }
    }
}
