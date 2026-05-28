package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.entity.Customer;

public interface NotificationService {

    void sendNotification(Customer customer, String message, String notificationType);
}
