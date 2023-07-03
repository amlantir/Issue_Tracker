package com.issue.tracker.notification;

import com.issue.tracker.authentication.UserService;
import com.issue.tracker.common.CustomModelMapper;
import com.issue.tracker.common.ViewerRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    private final CustomModelMapper modelMapper;

    private final UserService userService;

    @ViewerRole
    @GetMapping("/notifications")
    public List<NotificationDto> getAllNotificationsByName() {

        String username = userService.getCurrentUser().getUsername();

        List<Notification> notifications = notificationService.getAllNotificationsByUserName(username);

        return modelMapper.mapList(notifications, NotificationDto.class);
    }
}
