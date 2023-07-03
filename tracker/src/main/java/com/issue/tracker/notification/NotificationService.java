package com.issue.tracker.notification;

import com.issue.tracker.authentication.User;
import com.issue.tracker.ticket.Ticket;

import java.util.List;
import java.util.Set;

public interface NotificationService {

    List<Notification> getAllNotificationsByUserName(String userName);
    void createNotifications(Ticket ticket);
    void updateNotifications(Ticket ticket, Set<User> newAssignees);
    List<Notification> updateNotificationStatus(Ticket ticket);
    void deleteNotificationsByTicketId(Long ticketId);
}
