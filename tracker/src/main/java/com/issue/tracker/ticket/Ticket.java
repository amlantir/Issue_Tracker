package com.issue.tracker.ticket;

import com.issue.tracker.authentication.User;
import com.issue.tracker.common.BaseEntity;
import com.issue.tracker.project.Project;
import com.issue.tracker.ticket.priority.TicketPriority;
import com.issue.tracker.ticket.status.TicketStatus;
import com.issue.tracker.ticket.type.TicketType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @NotEmpty
    private String issueName;

    @NotEmpty
    private String description;

    @ManyToOne
    @JoinColumn(name="project_id", nullable=false)
    private Project project;

    @ManyToOne
    @JoinColumn(name="status_id", nullable=false)
    private TicketStatus ticketStatus;

    @ManyToOne
    @JoinColumn(name="type_id", nullable=false)
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name="priority_id", nullable=false)
    private TicketPriority ticketPriority;

    @ManyToMany
    @JoinTable(name = "ticket_users",
            joinColumns = @JoinColumn(name = "ticket_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> assignees;
}
