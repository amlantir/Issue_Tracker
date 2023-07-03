package com.issue.tracker.filesystem;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FileDataDto {

    @NotNull
    private Long id;

    @NotEmpty
    private String fileName;

    @NotEmpty
    private String fileType;
}
