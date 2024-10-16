package com.despereaux.jpascheduler.controller;

import com.despereaux.jpascheduler.entity.Comment;
import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.service.CommentService;
import com.despereaux.jpascheduler.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService sS;
    private final CommentService cS;

    public ScheduleController(ScheduleService sS, CommentService cS) {
        this.sS = sS;
        this.cS = cS;
    }
    // 일정 전체 조회
    @GetMapping
    public List<Schedule> getAllSchedules() {
        return sS.findAll();
    }
    // 일정 부분 조회
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        return sS.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // 일정 저장
    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody Schedule s) {
        s.setCreatedAt(LocalDateTime.now());
        s.setUpdatedAt(LocalDateTime.now());
        sS.save(s);
        return ResponseEntity.ok().build();
    }
    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule s) {
        sS.update(id, s);
        return ResponseEntity.ok().build();
    }
    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        sS.delete(id);
        return ResponseEntity.noContent().build();
    }
    // 댓글 구역
    // 댓글 조회 by 스케쥴
    @GetMapping("/{scheduleId}/comments")
    public List<Comment> getCommentsByScheduleId(@PathVariable Long scheduleId) {
        return cS.findAllBySchedulerId(scheduleId);
    }
    // 댓글 저장
    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<Void> createComment(@PathVariable Long scheduleId, @RequestBody Comment c) {
        sS.findById(scheduleId).ifPresent(c::setSchedule);
        c.setCreatedAt(LocalDateTime.now());
        c.setUpdatedAt(LocalDateTime.now());
        cS.save(c);
        return ResponseEntity.ok().build();
    }
    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody Comment c) {
        cS.update(commentId, c);
        return ResponseEntity.ok().build();
    }
    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        cS.delete(commentId);
        return ResponseEntity.noContent().build();
    }
}