package com.issue.tracker.ticket.comment;

import com.issue.tracker.common.CustomModelMapper;
import com.issue.tracker.common.ViewerRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
public class CommentController {

    private final CommentService commentService;

    private final CustomModelMapper modelMapper;

    @ViewerRole
    @PostMapping("/comments")
    public CommentDto createComment(@RequestBody @Valid CommentCreateDto commentCreateDto) {

        Comment comment = modelMapper.map(commentCreateDto, Comment.class);

        Comment createdComment = commentService.createComment(comment);

        return modelMapper.map(createdComment, CommentDto.class);
    }

    @ViewerRole
    @PostMapping("/comments/{id}")
    public CommentDto updateComment(@PathVariable Long id, @RequestBody @Valid CommentUpdateDto commentUpdateDto) {
        Comment comment = commentService.updateComment(id, commentUpdateDto);
        return modelMapper.map(comment, CommentDto.class);
    }

    @ViewerRole
    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
