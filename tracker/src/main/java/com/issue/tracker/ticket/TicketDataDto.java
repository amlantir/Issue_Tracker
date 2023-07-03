package com.issue.tracker.ticket;

import com.issue.tracker.authentication.UserDto;
import com.issue.tracker.filesystem.FileDataDto;
import com.issue.tracker.project.ProjectDto;
import com.issue.tracker.ticket.comment.CommentDto;
import com.issue.tracker.ticket.priority.TicketPriority;
import com.issue.tracker.ticket.status.TicketStatus;
import com.issue.tracker.ticket.type.TicketType;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
public class TicketDataDto {

    private Long id;
    private String createdBy;
    private String modifiedBy;
    private String issueName;
    private String description;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private ProjectDto project;
    private List<FileDataDto> files;
    private Set<UserDto> assignees;
    private List<CommentDto> comments;
    private TicketStatus ticketStatus;
    private TicketType ticketType;
    private TicketPriority ticketPriority;
    private Boolean isSeen;
}
