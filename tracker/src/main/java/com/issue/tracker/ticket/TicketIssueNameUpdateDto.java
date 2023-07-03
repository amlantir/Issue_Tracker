package com.issue.tracker.ticket;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TicketIssueNameUpdateDto {

    @NotEmpty
    private String issueName;
}
