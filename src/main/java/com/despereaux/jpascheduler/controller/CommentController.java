package com.despereaux.jpascheduler.controller;

import com.despereaux.jpascheduler.dto.CommentDto;
import com.despereaux.jpascheduler.entity.Comment;
import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> createComment(@RequestBody CommentDto commentDTO) {
        Optional<Schedule> optionalSchedule = commentService.getScheduleById(commentDTO.getScheduleId());
        if (optionalSchedule.isPresent()) {
            Comment comment = new Comment();
            comment.setContent(commentDTO.getContent());
            comment.setCreatedAt(LocalDateTime.now());
            comment.setSchedule(optionalSchedule.get());
            commentService.saveComment(comment);
            return ResponseEntity.ok("댓글이 성공적으로 생성되었습니다");
        }
        return ResponseEntity.badRequest().body("일정을 찾을 수 없습니다");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            CommentDto commentDTO = new CommentDto();
            commentDTO.setId(comment.get().getId());
            commentDTO.setContent(comment.get().getContent());
            commentDTO.setCreatedAt(comment.get().getCreatedAt());
            commentDTO.setUpdatedAt(comment.get().getUpdatedAt());
            commentDTO.setScheduleId(comment.get().getSchedule().getId());
            return ResponseEntity.ok(commentDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        List<CommentDto> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto commentDTO = new CommentDto();
            commentDTO.setId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setCreatedAt(comment.getCreatedAt());
            commentDTO.setUpdatedAt(comment.getUpdatedAt());
            commentDTO.setScheduleId(comment.getSchedule().getId());
            commentDTOs.add(commentDTO);
        }
        return ResponseEntity.ok(commentDTOs);
    }

    @PutMapping
    public ResponseEntity<String> updateComment(@RequestBody CommentDto commentDTO) {
        Optional<Comment> optionalComment = commentService.getCommentById(commentDTO.getId());
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(commentDTO.getContent());
            comment.setUpdatedAt(LocalDateTime.now());
            commentService.updateComment(comment);
            return ResponseEntity.ok("댓글이 성공적으로 업데이트되었습니다");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            commentService.deleteComment(comment.get());
            return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다");
        }
        return ResponseEntity.notFound().build();
    }
}