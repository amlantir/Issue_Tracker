package com.issue.tracker.ticket;

import com.issue.tracker.authentication.UserDto;
import com.issue.tracker.project.ProjectDto;
import com.issue.tracker.ticket.priority.TicketPriorityDto;
import com.issue.tracker.ticket.type.TicketTypeDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TicketCreateFillDataDto {

    private List<ProjectDto> projects;
    private List<UserDto> users;
    private List<TicketTypeDto> types;
    private List<TicketPriorityDto> priorities;
}
