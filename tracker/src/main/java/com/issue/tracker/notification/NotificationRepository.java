package com.issue.tracker.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserUsername(String userName);
    void deleteAllByUserIdIn(Collection<Long> userIdList);
    Optional<Notification> findNotificationByTicketIdAndUserId(Long ticketId, Long userId);
    void deleteAllByTicketId(Long ticketId);
}
