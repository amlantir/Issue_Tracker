package com.issue.tracker.filesystem;

import com.issue.tracker.common.BaseEntity;
import com.issue.tracker.ticket.Ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "file_datas")
@AllArgsConstructor
@NoArgsConstructor
public class FileData extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="ticket_id", nullable=false)
    private Ticket ticket;

    @NotEmpty
    private String filePath;

    @NotEmpty
    private String fileName;

    @NotEmpty
    private String fileType;

    @NotNull
    private Long fileSize;
}
