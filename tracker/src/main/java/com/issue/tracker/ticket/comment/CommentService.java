package com.issue.tracker.ticket.comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Comment comment);
    List<Comment> getAllCommentsByTicketId(Long ticketId);
    Comment updateComment(Long id, CommentUpdateDto comment);
    void deleteComment(Long id);
    void deleteCommentsByTicketId(Long ticketId);
}
