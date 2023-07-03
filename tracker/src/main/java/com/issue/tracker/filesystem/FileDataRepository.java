package com.issue.tracker.filesystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {
    List<FileData> findByTicketId(Long ticketId);
    void deleteAllByTicketId(Long ticketId);
}
