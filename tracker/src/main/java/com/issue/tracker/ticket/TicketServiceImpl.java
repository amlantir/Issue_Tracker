package com.issue.tracker.ticket;

import com.issue.tracker.authentication.User;
import com.issue.tracker.authentication.UserService;
import com.issue.tracker.common.CustomModelMapper;
import com.issue.tracker.common.RollbackOnException;
import com.issue.tracker.filesystem.FileData;
import com.issue.tracker.filesystem.FileDataService;
import com.issue.tracker.notification.Notification;
import com.issue.tracker.notification.NotificationService;
import com.issue.tracker.ticket.comment.CommentService;
import com.issue.tracker.ticket.priority.TicketPriorityEnum;
import com.issue.tracker.ticket.status.TicketStatusEnum;
import com.issue.tracker.ticket.type.TicketTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final NotificationService notificationService;
    private final UserService userService;
    private final CustomModelMapper modelMapper;
    private final FileDataService fileDataService;
    private final CommentService commentService;

    @RollbackOnException
    @Override
    public Ticket createTicket(Ticket ticket) {
        ticket.setTicketStatus(TicketStatusEnum.getTicketStatusByEnum(TicketStatusEnum.NEW));

        Ticket savedTicket = ticketRepository.save(ticket);

        notificationService.createNotifications(savedTicket);

        return savedTicket;
    }

    @Override
    public List<Ticket> getAllTicketsByAssignedUser(String username) {
        return ticketRepository.findAllByAssigneesUsernameOrderByIssueName(username);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAllByOrderByIssueName();
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Ticket not found"));
    }

    @Override
    public List<TicketDataDto> getAllTicketDataDto(boolean isAllData) {
        String username = userService.getCurrentUser().getUsername();

        List<Ticket> tickets = isAllData ? this.getAllTickets() : this.getAllTicketsByAssignedUser(username);

        List<Notification> notifications = notificationService.getAllNotificationsByUserName(username);

        List<TicketDataDto> ticketDataDtoList = modelMapper.mapList(tickets, TicketDataDto.class);

        for (TicketDataDto ticketDataDto : ticketDataDtoList) {
            Optional<Notification> notificationOptional = notifications
                    .stream().filter(x -> x.getTicket().getId().equals(ticketDataDto.getId())).findFirst();
            if (notificationOptional.isPresent()) {
                Notification notification = notificationOptional.get();
                ticketDataDto.setIsSeen(notification.getIsSeen());
            } else {
                ticketDataDto.setIsSeen(true);
            }
        }

        return ticketDataDtoList;
    }

    @RollbackOnException
    @Override
    public void updateTicketDescription(Long id, TicketDescriptionUpdateDto ticketDescriptionUpdateDto) {
        Ticket ticket = this.getTicketById(id);
        ticket.setDescription(ticketDescriptionUpdateDto.getDescription());

        ticketRepository.save(ticket);
    }

    @RollbackOnException
    @Override
    public void updateTicketIssueName(Long id, TicketIssueNameUpdateDto ticketIssueNameUpdateDto) {
        Ticket ticket = this.getTicketById(id);
        ticket.setIssueName(ticketIssueNameUpdateDto.getIssueName());

        ticketRepository.save(ticket);
    }

    @RollbackOnException
    @Override
    public void updateTicketAssignees(Long id, Set<User> assignees) {
        Ticket ticket = this.getTicketById(id);

        notificationService.updateNotifications(ticket, assignees);

        ticket.setAssignees(assignees);
        ticketRepository.save(ticket);
    }

    @RollbackOnException
    @Override
    public void updateTicketStatus(Long id, Long ticketStatusId) {
        boolean isLegitValue = TicketStatusEnum.getLookup().containsKey(ticketStatusId);
        if (!isLegitValue) {
            throw new NoSuchElementException("Element does not exist");
        }
        Ticket ticket = this.getTicketById(id);
        ticket.setTicketStatus(TicketStatusEnum.getTicketStatusByEnum(TicketStatusEnum.get(ticketStatusId)));

        ticketRepository.save(ticket);
    }

    @RollbackOnException
    @Override
    public void updateTicketType(Long id, Long ticketTypeId) {
        boolean isLegitValue = TicketTypeEnum.getLookup().containsKey(ticketTypeId);
        if (!isLegitValue) {
            throw new NoSuchElementException("Element does not exist");
        }
        Ticket ticket = this.getTicketById(id);
        ticket.setTicketType(TicketTypeEnum.getTicketTypeByEnum(TicketTypeEnum.get(ticketTypeId)));

        ticketRepository.save(ticket);
    }

    @RollbackOnException
    @Override
    public void updateTicketPriority(Long id, Long ticketPriorityId) {
        boolean isLegitValue = TicketPriorityEnum.getLookup().containsKey(ticketPriorityId);
        if (!isLegitValue) {
            throw new NoSuchElementException("Element does not exist");
        }
        Ticket ticket = this.getTicketById(id);
        ticket.setTicketPriority(TicketPriorityEnum.getTicketPriorityByEnum(TicketPriorityEnum.get(ticketPriorityId)));

        ticketRepository.save(ticket);
    }

    @RollbackOnException
    @Override
    public void deleteTicket(Long id) throws IOException {
        List<FileData> fileDataList = fileDataService.getFileDataByTicketId(id);

        for (FileData fileData : fileDataList) {
            fileDataService.deleteFile(fileData.getId());
        }

        notificationService.deleteNotificationsByTicketId(id);
        commentService.deleteCommentsByTicketId(id);
        fileDataService.deleteFileDataByTicketId(id);
        ticketRepository.deleteById(id);
    }
}
