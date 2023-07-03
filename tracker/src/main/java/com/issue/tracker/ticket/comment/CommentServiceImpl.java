package com.issue.tracker.ticket.comment;

import com.issue.tracker.common.AuthenticationService;
import com.issue.tracker.common.RollbackOnException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final AuthenticationService authenticationService;

    @RollbackOnException
    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllCommentsByTicketId(Long ticketId) {
        return commentRepository.findAllByTicketIdOrderByCreatedAt(ticketId);
    }

    @RollbackOnException
    @Override
    public Comment updateComment(Long id, CommentUpdateDto commentUpdateDto) {

        Comment comment = checkIfSameUser(id);
        comment.setTicketComment(commentUpdateDto.getTicketComment());

        return commentRepository.save(comment);
    }

    @RollbackOnException
    @Override
    public void deleteComment(Long id) {

        checkIfSameUser(id);

        commentRepository.deleteById(id);
    }

    @Override
    public void deleteCommentsByTicketId(Long ticketId) {
        commentRepository.deleteAllByTicketId(ticketId);
    }

    private Comment checkIfSameUser(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));

        String userName = authenticationService.getAuthentication().getName();
        if (!comment.getCreatedBy().equals(userName)) {
            throw new AccessDeniedException("Not created by the same user!");
        }
        return comment;
    }
}
