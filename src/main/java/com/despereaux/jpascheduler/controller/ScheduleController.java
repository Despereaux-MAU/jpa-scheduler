package com.despereaux.jpascheduler.controller;

import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    // 일정 전체 조회
    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.findAll();
    }
    // 일정 부분 조회
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        return scheduleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // 일정 저장
    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody Schedule s) {
        s.setCreatedAt(LocalDateTime.now());
        s.setUpdatedAt(LocalDateTime.now());
        scheduleService.save(s);
        return ResponseEntity.ok().build();
    }
    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule s) {
        scheduleService.update(id, s);
        return ResponseEntity.ok().build();
    }
    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
