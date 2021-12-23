package com.momot.trakball.service;

import com.momot.trakball.dao.Comment;
import com.momot.trakball.dao.Squad;
import com.momot.trakball.dao.User;
import com.momot.trakball.dto.CommentDto;
import com.momot.trakball.dto.request.DeleteCommentRequest;
import com.momot.trakball.dto.response.MessageResponse;
import com.momot.trakball.repository.CommentRepository;
import com.momot.trakball.repository.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final SquadRepository squadRepository;
    private final CommentRepository commentRepository;

    private final UserService userService;

    @Autowired
    public CommentService(SquadRepository squadRepository, CommentRepository commentRepository, UserService userService) {
        this.squadRepository = squadRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public List<CommentDto> findCommentsById(Long squad_id) {
        Optional<Squad> squad = squadRepository.findById(squad_id);
        List<CommentDto> result = new ArrayList<>();
        squad.ifPresent(value -> result.addAll(value.getComments().stream().map(
                CommentDto::new
        ).sorted().collect(Collectors.toList())));
        Collections.reverse(result);
        return result;
    }

    public ResponseEntity<?> addComment(Long squad_id, CommentDto commentDto) {
        Optional<Squad> squad = squadRepository.findById(squad_id);
        Optional<User> user = userService.getUserFromContext();

        if (squad.isPresent() && user.isPresent()) {
            Comment newComment = new Comment(commentDto.getText(), commentDto.getDate(), user.get(), squad.get());
            commentRepository.save(newComment);
            return ResponseEntity.ok(new CommentDto(newComment));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Comment wasn't posted. Something went wrong."));
    }

    public ResponseEntity<?> deleteComment(DeleteCommentRequest deleteCommentRequest) {
        Optional<Comment> comment = commentRepository.findById(deleteCommentRequest.getComment_id());
        Optional<User> user = userService.getUserFromContext();

        if (comment.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Comment not found 🤐"));
        }

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("User session problem"));
        }

        if (comment.get().getCreator().getUserId().equals(user.get().getUserId())) {
            commentRepository.deleteById(deleteCommentRequest.getComment_id());
            return ResponseEntity.ok(new MessageResponse("You've deleted a comment."));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Comment wasn't deleted. Something went wrong."));
    }
}