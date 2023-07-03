package com.issue.tracker.filesystem;

import com.issue.tracker.ticket.Ticket;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileDataService {

    List<FileDataDto> createFileData(MultipartFile[] files, Ticket ticket) throws IOException;
    List<FileData> getFileDataByTicketId(Long ticketId);
    void deleteFile(Long id) throws IOException;
    FileData getFileData(Long id) throws IOException;
    void deleteFileDataByTicketId(Long ticketId);

}
