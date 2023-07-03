package com.issue.tracker.ticket.comment;

import com.issue.tracker.common.BaseEntity;
import com.issue.tracker.ticket.Ticket;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ticket_comments")
public class Comment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="ticket_id", nullable=false)
    private Ticket ticket;

    @NotEmpty
    private String ticketComment;
}
