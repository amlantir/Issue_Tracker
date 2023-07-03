package com.issue.tracker.ticket;

import com.issue.tracker.authentication.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class TicketCreateDto {

    @NotEmpty
    private String issueName;

    @NotEmpty
    private String description;

    @NotNull
    private Long projectId;

    @NotNull
    private Long typeId;

    @NotNull
    private Long priorityId;

    private Set<User> assignees;
}
