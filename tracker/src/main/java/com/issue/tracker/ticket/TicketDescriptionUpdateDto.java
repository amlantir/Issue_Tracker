package com.issue.tracker.ticket;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TicketDescriptionUpdateDto {

    @NotEmpty
    private String description;
}
