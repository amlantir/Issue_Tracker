package com.issue.tracker.notification;

import com.issue.tracker.authentication.User;
import com.issue.tracker.authentication.UserService;
import com.issue.tracker.common.RollbackOnException;
import com.issue.tracker.ticket.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Override
    public List<Notification> getAllNotificationsByUserName(String userName) {
        return notificationRepository.findAllByUserUsername(userName);
    }

    @RollbackOnException
    @Override
    public void createNotifications(Ticket ticket) {
        Set<User> ticketAssignees = ticket.getAssignees();
        User currentUser = userService.getCurrentUser();

        List<Notification> notifications = new ArrayList<>();
        for (User assignee : ticketAssignees) {
            if (!assignee.getId().equals(currentUser.getId())) {
                Notification notification = new Notification(ticket, assignee, false);
                notifications.add(notification);
            }
        }
        notificationRepository.saveAll(notifications);
    }

    @RollbackOnException
    @Override
    public void updateNotifications(Ticket ticket, Set<User> newAssignees) {
        List<Long> updateAssigneeIds = newAssignees.stream().map(User::getId).toList();
        List<Long> ticketAssigneeIds = ticket.getAssignees().stream().map(User::getId).toList();

        List<Long> toBeDeleted = new ArrayList<>(CollectionUtils.removeAll(ticketAssigneeIds, updateAssigneeIds));
        List<Long> toBeAdded = new ArrayList<>(CollectionUtils.removeAll(updateAssigneeIds, ticketAssigneeIds));

        List<Notification> notifications = new ArrayList<>();
        for (User assignee : newAssignees) {
            boolean isNewNotification =
                    !userService.getCurrentUser().getId().equals(assignee.getId()) &&
                            toBeAdded.contains(assignee.getId());

            if (isNewNotification) {
                Notification notification = new Notification(ticket, assignee, false);
                notifications.add(notification);
            }
        }

        notificationRepository.saveAll(notifications);
        notificationRepository.deleteAllByUserIdIn(toBeDeleted);
    }

    @Override
    public List<Notification> updateNotificationStatus(Ticket ticket) {
        List<Notification> notifications = notificationRepository
                .findAllByUserUsername(userService.getCurrentUser().getUsername());

        for (Notification notification : notifications) {
            boolean isCurrentTicketNotification = ticket.getId().equals(notification.getTicket().getId());
            if (isCurrentTicketNotification) {
                notification.setIsSeen(true);
                notificationRepository.save(notification);
            }
        }
        return notifications;
    }

    @Override
    public void deleteNotificationsByTicketId(Long ticketId) {
        notificationRepository.deleteAllByTicketId(ticketId);
    }
}
