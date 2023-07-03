package com.issue.tracker.notification;

import com.issue.tracker.authentication.UserDto;
import com.issue.tracker.ticket.TicketDataDto;
import lombok.Data;

@Data
public class NotificationDto {

    private TicketDataDto ticket;
    private UserDto user;
    private Boolean isSeen;
}
