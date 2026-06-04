package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.entity.Notification;
import com.utilityinternational.utility_backend.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification updateNotification(Long id, Notification notification) {

        Notification existing = getNotificationById(id);

        existing.setMessage(notification.getMessage());
        existing.setNotificationType(notification.getNotificationType());
        existing.setCustomer(notification.getCustomer());

        return notificationRepository.save(existing);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}