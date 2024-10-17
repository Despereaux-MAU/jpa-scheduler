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
    private CommentService cS;
    // 댓글 생성
    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentDto cDto) {
        Optional<Schedule> optionalSchedule = cS.getScheduleById(cDto.getScheduleId());
        if (optionalSchedule.isPresent()) {
            Comment comment = new Comment();
            comment.setContent(cDto.getContent());
            comment.setCreatedAt(LocalDateTime.now());
            comment.setSchedule(optionalSchedule.get());
            cS.saveComment(comment);
            return ResponseEntity.ok("댓글이 성공적으로 생성되었습니다");
        }
        return ResponseEntity.badRequest().body("일정을 찾을 수 없습니다");
    }
    // 댓글 조회(ID)
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long id) {
        Optional<Comment> comment = cS.getCommentById(id);
        if (comment.isPresent()) {
            CommentDto cDto = new CommentDto();
            cDto.setId(comment.get().getId());
            cDto.setContent(comment.get().getContent());
            cDto.setCreatedAt(comment.get().getCreatedAt());
            cDto.setUpdatedAt(comment.get().getUpdatedAt());
            cDto.setScheduleId(comment.get().getSchedule().getId());
            return ResponseEntity.ok(cDto);
        }
        return ResponseEntity.notFound().build();
    }
    // 전체 조회
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<Comment> comments = cS.getAllComments();
        List<CommentDto> cDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto cDto = new CommentDto();
            cDto.setId(comment.getId());
            cDto.setContent(comment.getContent());
            cDto.setCreatedAt(comment.getCreatedAt());
            cDto.setUpdatedAt(comment.getUpdatedAt());
            cDto.setScheduleId(comment.getSchedule().getId());
            cDtos.add(cDto);
        }
        return ResponseEntity.ok(cDtos);
    }
    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody CommentDto cDto) {
        Optional<Comment> optionalComment = cS.getCommentById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(cDto.getContent());
            comment.setUpdatedAt(LocalDateTime.now());
            cS.updateComment(comment);
            return ResponseEntity.ok("댓글이 성공적으로 업데이트되었습니다");
        }
        return ResponseEntity.notFound().build();
    }
    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        Optional<Comment> optionalComment = cS.getCommentById(id);
        if (optionalComment.isPresent()) {
            cS.deleteComment(optionalComment.get());
            return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다");
        }
        return ResponseEntity.notFound().build();
    }
}
