package com.issue.tracker.ticket;

import com.issue.tracker.authentication.User;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface TicketService {

    Ticket createTicket(Ticket ticket);
    List<Ticket> getAllTickets();
    List<Ticket> getAllTicketsByAssignedUser(String username);
    Ticket getTicketById(Long id);
    List<TicketDataDto> getAllTicketDataDto(boolean isAllData);
    void updateTicketDescription(Long id, TicketDescriptionUpdateDto ticketDescriptionUpdateDto);
    void updateTicketIssueName(Long id, TicketIssueNameUpdateDto ticketIssueNameUpdateDto);
    void updateTicketAssignees(Long id, Set<User> assignees);
    void updateTicketStatus(Long id, Long ticketStatusId);
    void updateTicketType(Long id, Long ticketTypeId);
    void updateTicketPriority(Long id, Long ticketPriorityId);
    void deleteTicket(Long id) throws IOException;
}
