package com.despereaux.jpascheduler.controller;

import com.despereaux.jpascheduler.dto.ScheduleDto;
import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.entity.User;
import com.despereaux.jpascheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<String> createSchedule(@RequestBody ScheduleDto scheduleDTO) {
        Optional<User> userOptional = scheduleService.getUserById(scheduleDTO.getUserId());
        if (userOptional.isPresent()) {
            Schedule schedule = new Schedule();
            schedule.setUser(userOptional.get());
            schedule.setTitle(scheduleDTO.getTitle());
            schedule.setContent(scheduleDTO.getContent());
            schedule.setCreatedAt(LocalDateTime.now());
            scheduleService.saveSchedule(schedule);
            return ResponseEntity.ok("일정이 성공적으로 생성되었습니다");
        }
        return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getSchedule(@PathVariable Long id) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        if (schedule.isPresent()) {
            ScheduleDto scheduleDTO = new ScheduleDto();
            scheduleDTO.setId(schedule.get().getId());
            scheduleDTO.setUserId(schedule.get().getUser().getId());
            scheduleDTO.setTitle(schedule.get().getTitle());
            scheduleDTO.setContent(schedule.get().getContent());
            scheduleDTO.setCreatedAt(schedule.get().getCreatedAt());
            scheduleDTO.setUpdatedAt(schedule.get().getUpdatedAt());
            scheduleDTO.setCommentCount(schedule.get().getComments().size());
            return ResponseEntity.ok(scheduleDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDto>> getAllSchedules(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Schedule> schedules = scheduleService.getSchedules(pageable);
        List<ScheduleDto> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleDto scheduleDTO = new ScheduleDto();
            scheduleDTO.setId(schedule.getId());
            scheduleDTO.setUserId(schedule.getUser().getId());
            scheduleDTO.setTitle(schedule.getTitle());
            scheduleDTO.setContent(schedule.getContent());
            scheduleDTO.setCreatedAt(schedule.getCreatedAt());
            scheduleDTO.setUpdatedAt(schedule.getUpdatedAt());
            scheduleDTO.setCommentCount(schedule.getComments().size());
            scheduleDTOs.add(scheduleDTO);
        }
        return ResponseEntity.ok(scheduleDTOs);
    }

    @PutMapping
    public ResponseEntity<String> updateSchedule(@RequestBody ScheduleDto scheduleDTO) {
        Optional<Schedule> optionalSchedule = scheduleService.getScheduleById(scheduleDTO.getId());
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setTitle(scheduleDTO.getTitle());
            schedule.setContent(scheduleDTO.getContent());
            schedule.setUpdatedAt(LocalDateTime.now());
            scheduleService.updateSchedule(schedule);
            return ResponseEntity.ok("일정이 성공적으로 업데이트되었습니다");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        if (schedule.isPresent()) {
            scheduleService.deleteSchedule(schedule.get());
            return ResponseEntity.ok("일정이 성공적으로 삭제되었습니다");
        }
        return ResponseEntity.notFound().build();
    }
}