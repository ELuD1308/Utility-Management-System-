package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(Notification notification);

    Notification getNotificationById(Long id);

    List<Notification> getAllNotifications();

    Notification updateNotification(Long id, Notification notification);

    void deleteNotification(Long id);
}
