package com.utilityinternational.utility_backend.notification.notification;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.utilityinternational.utility_backend.entity.Customer;
import com.utilityinternational.utility_backend.entity.Notification;
import com.utilityinternational.utility_backend.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService implements com.utilityinternational.utility_backend.service.NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendNotification(Customer customer,
                                 String message,
                                 String notificationType) {
        try {
            Notification notification = Notification.builder()
                    .customer(customer)
                    .message(message)
                    .notificationType(notificationType)
                    .build();

            notificationRepository.save(notification);

            log.info("Notification [{}] saved for customer {}",
                    notificationType, customer.getId());

        } catch (Exception e) {
            log.error("Failed to send notification [{}] to customer {}: {}",
                    notificationType, customer.getId(), e.getMessage());
        }
    }
}
