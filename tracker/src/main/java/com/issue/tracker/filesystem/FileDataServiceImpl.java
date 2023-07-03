package com.issue.tracker.filesystem;

import com.issue.tracker.common.Constants;
import com.issue.tracker.common.CustomModelMapper;
import com.issue.tracker.common.RollbackOnException;
import com.issue.tracker.ticket.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileDataServiceImpl implements FileDataService {

    private final FileDataRepository fileDataRepository;

    private final CustomModelMapper modelMapper;

    @RollbackOnException
    @Override
    public List<FileDataDto> createFileData(MultipartFile[] files, Ticket ticket) throws IOException {

        if (ArrayUtils.isEmpty(files)) {
            return Collections.emptyList();
        }

        boolean isDuplicateUpload = Arrays.stream(files).map(MultipartFile::getOriginalFilename).distinct().count()
                != files.length;

        boolean isFileExist = Arrays.stream(files).allMatch(file ->
                Files.exists(Path.of(Constants.FILE_LOCATION + file.getOriginalFilename())));

        if (isDuplicateUpload || isFileExist) {
            throw new FileAlreadyExistsException("File already exists!");
        }

        List<FileData> fileDataSaveList = new ArrayList<>();
        for (MultipartFile file : files) {
            FileData fileData = new FileData(ticket, Constants.FILE_LOCATION,
                    file.getOriginalFilename(), new Tika().detect(file.getBytes()), file.getSize());
            fileDataSaveList.add(fileData);

            Path path = Paths.get(Constants.FILE_LOCATION + file.getOriginalFilename());
            Files.write(path, file.getBytes());
        }

        fileDataRepository.saveAll(fileDataSaveList);

        return modelMapper.mapList(fileDataSaveList, FileDataDto.class);
    }

    @Override
    public List<FileData> getFileDataByTicketId(Long ticketId) {
        return fileDataRepository.findByTicketId(ticketId);
    }

    @RollbackOnException
    @Override
    public void deleteFile(Long id) throws IOException {

        Optional<FileData> fileDataOptional = fileDataRepository.findById(id);
        if (fileDataOptional.isPresent()) {
            FileData fileData = fileDataOptional.get();
            fileDataRepository.delete(fileData);
            Path path = Paths.get(fileData.getFilePath() + fileData.getFileName());
            Files.delete(path);
        }
    }

    @Override
    public FileData getFileData(Long id) {
        return fileDataRepository.findById(id).orElseThrow(() -> new NoSuchElementException("File not found"));
    }

    @Override
    public void deleteFileDataByTicketId(Long ticketId) {
        fileDataRepository.deleteAllByTicketId(ticketId);
    }
}
