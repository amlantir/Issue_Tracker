package com.issue.tracker.ticket;

import com.issue.tracker.authentication.User;
import com.issue.tracker.authentication.UserDto;
import com.issue.tracker.authentication.UserService;
import com.issue.tracker.common.*;
import com.issue.tracker.filesystem.FileData;
import com.issue.tracker.filesystem.FileDataDto;
import com.issue.tracker.filesystem.FileDataService;
import com.issue.tracker.notification.Notification;
import com.issue.tracker.notification.NotificationDto;
import com.issue.tracker.notification.NotificationService;
import com.issue.tracker.project.Project;
import com.issue.tracker.project.ProjectDto;
import com.issue.tracker.project.ProjectService;
import com.issue.tracker.ticket.comment.Comment;
import com.issue.tracker.ticket.comment.CommentDto;
import com.issue.tracker.ticket.comment.CommentService;
import com.issue.tracker.ticket.priority.TicketPriorityDto;
import com.issue.tracker.ticket.priority.TicketPriorityEnum;
import com.issue.tracker.ticket.status.TicketStatusDto;
import com.issue.tracker.ticket.status.TicketStatusEnum;
import com.issue.tracker.ticket.type.TicketTypeDto;
import com.issue.tracker.ticket.type.TicketTypeEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@RestController
public class TicketController {

    private final TicketService ticketService;
    private final FileDataService fileDataService;
    private final CustomModelMapper modelMapper;
    private final ProjectService projectService;
    private final UserService userService;
    private final CommentService commentService;
    private final NotificationService notificationService;

    @UserRole
    @RollbackOnException
    @PostMapping("/tickets")
    public void createTicket(@RequestPart @Valid TicketCreateDto ticketCreateDto,
                             @RequestPart(required = false) MultipartFile[] files) throws IOException {

        Ticket ticketRequest = modelMapper.map(ticketCreateDto, Ticket.class);

        Ticket ticket = ticketService.createTicket(ticketRequest);

        fileDataService.createFileData(files, ticket);
    }

    @ViewerRole
    @GetMapping("/tickets/name")
    public List<TicketDataDto> getAllTicketsByName() {
        return ticketService.getAllTicketDataDto(false);
    }

    @ViewerRole
    @GetMapping("/tickets")
    public List<TicketDataDto> getAllTickets() {
        return ticketService.getAllTicketDataDto(true);
    }

    @ViewerRole
    @GetMapping("/tickets/id/{id}")
    public TicketSinglePageDataDto getTicketById(@PathVariable Long id) {

        Ticket ticket = ticketService.getTicketById(id);

        List<FileData> fileDataList = fileDataService.getFileDataByTicketId(id);
        List<FileDataDto> fileDataDtoList = modelMapper.mapList(fileDataList, FileDataDto.class);

        List<Comment> comments = commentService.getAllCommentsByTicketId(id);
        List<CommentDto> commentDtoList = modelMapper.mapList(comments, CommentDto.class);

        TicketDataDto ticketDataDto = modelMapper.map(ticket, TicketDataDto.class);

        ticketDataDto.setFiles(fileDataDtoList);
        ticketDataDto.setComments(commentDtoList);

        List<UserDto> allUsers = modelMapper.mapList(userService.getAllUsers(), UserDto.class);

        List<Notification> notifications = notificationService.updateNotificationStatus(ticket);
        List<NotificationDto> notificationDtoList = modelMapper.mapList(notifications, NotificationDto.class);

        return new TicketSinglePageDataDto(ticketDataDto, allUsers, TicketStatusEnum.getTicketStatusList(),
                TicketTypeEnum.getTicketTypeList(), TicketPriorityEnum.getTicketPriorityList(), notificationDtoList);
    }

    @AdminRole
    @DeleteMapping("/tickets/id/{id}/{isAllTickets}")
    public List<TicketDataDto> deleteTicketById(@PathVariable Long id, @PathVariable boolean isAllTickets)
            throws IOException {
        ticketService.deleteTicket(id);
        return ticketService.getAllTicketDataDto(isAllTickets);
    }

    @ViewerRole
    @GetMapping("/tickets/fill-create-ticket-data")
    public TicketCreateFillDataDto getCreateTicketSelectData() {

        List<Project> projects = projectService.getAllProjects();

        List<User> users = userService.getAllUsers();

        List<ProjectDto> projectDtoList = modelMapper.mapList(projects, ProjectDto.class);

        List<UserDto> userDtoList = modelMapper.mapList(users, UserDto.class);

        List<TicketTypeDto> ticketTypeDtoList = modelMapper.mapList(TicketTypeEnum.getTicketTypeList(),
                TicketTypeDto.class);

        List<TicketPriorityDto> ticketPriorityDtoList = modelMapper.mapList(TicketPriorityEnum.getTicketPriorityList(),
                TicketPriorityDto.class);

        return new TicketCreateFillDataDto(projectDtoList, userDtoList, ticketTypeDtoList, ticketPriorityDtoList);
    }

    @UserRole
    @PostMapping("/tickets/description/id/{id}")
    public void updateTicketDescription(
            @PathVariable Long id,
            @RequestBody @Valid TicketDescriptionUpdateDto ticketDescriptionUpdateDto) {

        ticketService.updateTicketDescription(id, ticketDescriptionUpdateDto);
    }

    @ViewerRole
    @PostMapping("/tickets/issue-name/id/{id}")
    public void updateTicketIssueName(
            @PathVariable Long id,
            @RequestBody @Valid TicketIssueNameUpdateDto ticketDescriptionUpdateDto) {

        ticketService.updateTicketIssueName(id, ticketDescriptionUpdateDto);
    }

    @UserRole
    @RollbackOnException
    @PostMapping("/tickets/assignees/id/{id}")
    public void updateTicketAssignees(@PathVariable Long id, @RequestBody Set<UserDto> assigneesDto) {

        Set<User> assignees = modelMapper.mapSet(assigneesDto, User.class);

        ticketService.updateTicketAssignees(id, assignees);
    }

    @UserRole
    @PostMapping("/tickets/status/id/{id}")
    public void updateTicketStatus(@PathVariable Long id, @RequestBody TicketStatusDto ticketStatusDto) {
        ticketService.updateTicketStatus(id, ticketStatusDto.getTicketStatusId());
    }

    @UserRole
    @PostMapping("/tickets/type/id/{id}")
    public void updateTicketType(@PathVariable Long id, @RequestBody TicketTypeDto ticketTypeDto) {
        ticketService.updateTicketType(id, ticketTypeDto.getTicketTypeId());
    }

    @AdminRole
    @PostMapping("/tickets/priority/id/{id}")
    public void updateTicketPriority(@PathVariable Long id, @RequestBody TicketPriorityDto ticketPriorityDto) {
        ticketService.updateTicketPriority(id, ticketPriorityDto.getTicketPriorityId());
    }
}
