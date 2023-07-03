package com.issue.tracker.ticket.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreateDto {

    @NotNull
    private Long ticketId;

    @NotEmpty
    private String ticketComment;
}
