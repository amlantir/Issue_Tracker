package com.issue.tracker.ticket.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentUpdateDto {

    @NotEmpty
    private String ticketComment;
}
