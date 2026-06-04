package com.utilityinternational.utility_backend.util;

import com.utilityinternational.utility_backend.security.service.CustomerDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static String getCurrentFullName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        return auth.getName();
    }

    public static Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomerDetailsImpl userDetails) {
            return userDetails.getId();
        }
        throw new IllegalStateException("No authenticated user found");
    }
}
