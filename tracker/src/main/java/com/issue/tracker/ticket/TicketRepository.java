package com.issue.tracker.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByOrderByIssueName();
    List<Ticket> findAllByAssigneesUsernameOrderByIssueName(String username);
}
