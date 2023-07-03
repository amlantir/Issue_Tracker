package com.issue.tracker.ticket;

import lombok.Data;

import java.util.Set;

@Data
public class TicketCreateAssigneeDto {

    private Set<Long> assignees;
}
