package com.issue.tracker.filesystem;

import com.issue.tracker.common.AdminRole;
import com.issue.tracker.common.UserRole;
import com.issue.tracker.common.ViewerRole;
import com.issue.tracker.ticket.Ticket;
import com.issue.tracker.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class FileController {

    private final FileDataService fileDataService;
    private final TicketService ticketService;

    @UserRole
    @PostMapping("/filedatas/{ticketId}")
    public List<FileDataDto> addFileToTicket(@PathVariable Long ticketId,
                                             @RequestBody MultipartFile[] files) throws IOException {

        Ticket ticket = ticketService.getTicketById(ticketId);

        return fileDataService.createFileData(files, ticket);
    }

    @AdminRole
    @DeleteMapping("/filedatas/{id}")
    public void deleteTicketFile(@PathVariable Long id) throws IOException {
        fileDataService.deleteFile(id);
    }

    @ViewerRole
    @GetMapping("/filedatas/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {

        FileData fileData = fileDataService.getFileData(id);

        Path path = Paths.get(fileData.getFilePath() + fileData.getFileName());
        File file = path.toFile();
        byte[] data = FileUtils.readFileToByteArray(file);

        String fileName = URLEncoder
                .encode(fileData.getFileName(), StandardCharsets.UTF_8).replace("+", "%20");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType(fileData.getFileType()));
        header.setContentLength(file.length());
        header.add("Access-Control-Expose-Headers", "Content-Disposition");
        header.set("Content-Disposition", "attachment; filename=" + fileName);

        return new ResponseEntity<>(data, header, HttpStatus.OK);
    }
}
