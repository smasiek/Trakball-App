package com.momot.trakball.controller;

import com.momot.trakball.dto.CommentDto;
import com.momot.trakball.dto.request.DeleteCommentRequest;
import com.momot.trakball.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/comments")
public class CommentsController {

    private final CommentService commentService;

    @Autowired
    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public Iterable<CommentDto> getComments(@RequestParam Long squad_id) {
        return commentService.findCommentsById(squad_id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> postComment(@RequestParam Long squad_id, @RequestBody CommentDto commentDto) {
        return commentService.addComment(squad_id, commentDto);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteComment(@RequestBody DeleteCommentRequest deleteCommentRequest) {
        return commentService.deleteComment(deleteCommentRequest);
    }
}