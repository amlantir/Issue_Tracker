package com.issue.tracker.filesystem;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FileDataUpdateDto {

    @NotEmpty
    private String fileName;
}
