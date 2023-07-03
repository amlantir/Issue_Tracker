package com.issue.tracker.ticket;

import com.issue.tracker.authentication.UserDto;
import com.issue.tracker.notification.NotificationDto;
import com.issue.tracker.ticket.priority.TicketPriority;
import com.issue.tracker.ticket.status.TicketStatus;
import com.issue.tracker.ticket.type.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TicketSinglePageDataDto {

    private TicketDataDto ticketData;
    private List<UserDto> potentialAssignees;
    private List<TicketStatus> potentialStatuses;
    private List<TicketType> potentialTypes;
    private List<TicketPriority> potentialPriorities;
    private List<NotificationDto> notifications;
}
