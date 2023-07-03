package com.issue.tracker.ticket.comment;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CommentDto {

    private Long id;
    private String ticketComment;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String createdBy;
    private String modifiedBy;
}
