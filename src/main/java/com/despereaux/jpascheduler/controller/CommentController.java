package com.despereaux.jpascheduler.controller;

import com.despereaux.jpascheduler.dto.CommentDto;
import com.despereaux.jpascheduler.entity.Comment;
import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public String createComment(@RequestBody CommentDto commentDTO) {
        Optional<Schedule> optionalSchedule = commentService.getScheduleById(commentDTO.getScheduleId());
        if (optionalSchedule.isPresent()) {
            Comment comment = new Comment();
            comment.setUsername(commentDTO.getUsername());
            comment.setContent(commentDTO.getContent());
            comment.setCreatedAt(LocalDateTime.now());
            comment.setSchedule(optionalSchedule.get());
            commentService.saveComment(comment);
            return "댓글이 생성되었습니다.";
        }
        throw new RuntimeException("댓글을 찾을 수 없습니다.");
    }

    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            CommentDto commentDTO = new CommentDto();
            commentDTO.setId(comment.get().getId());
            commentDTO.setUsername(comment.get().getUsername());
            commentDTO.setContent(comment.get().getContent());
            commentDTO.setCreatedAt(comment.get().getCreatedAt());
            commentDTO.setUpdatedAt(comment.get().getUpdatedAt());
            commentDTO.setScheduleId(comment.get().getSchedule().getId());
            return commentDTO;
        }
        throw new RuntimeException("댓글을 찾을 수 없습니다.");
    }

    @GetMapping
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        List<CommentDto> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto commentDTO = new CommentDto();
            commentDTO.setId(comment.getId());
            commentDTO.setUsername(comment.getUsername());
            commentDTO.setContent(comment.getContent());
            commentDTO.setCreatedAt(comment.getCreatedAt());
            commentDTO.setUpdatedAt(comment.getUpdatedAt());
            commentDTO.setScheduleId(comment.getSchedule().getId());
            commentDTOs.add(commentDTO);
        }
        return commentDTOs;
    }

    @PutMapping
    public String updateComment(@RequestBody CommentDto commentDTO) {
        Optional<Comment> optionalComment = commentService.getCommentById(commentDTO.getId());
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(commentDTO.getContent());
            comment.setUpdatedAt(LocalDateTime.now());
            commentService.updateComment(comment);
            return "댓글이 수정되었습니다.";
        }
        throw new RuntimeException("댓글을 찾을 수 없습니다.");
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            commentService.deleteComment(comment.get());
            return "댓글이 삭제되었습니다.";
        }
        throw new RuntimeException("댓글을 찾을 수 없습니다.");
    }
}