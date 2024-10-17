package com.despereaux.jpascheduler.controller;

import com.despereaux.jpascheduler.dto.ScheduleDto;
import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.entity.User;
import com.despereaux.jpascheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService sS;
    // 일정 생성
    @PostMapping
    public ResponseEntity<String> createSchedule(@RequestBody ScheduleDto scheduleDTO) {
        Optional<User> userOptional = sS.getUserById(scheduleDTO.getUserId());
        if (userOptional.isPresent()) {
            Schedule schedule = new Schedule();
            schedule.setUser(userOptional.get());
            schedule.setTitle(scheduleDTO.getTitle());
            schedule.setContent(scheduleDTO.getContent());
            schedule.setCreatedAt(LocalDateTime.now());
            schedule.setStatus("진행 중");
            sS.saveSchedule(schedule);
            return ResponseEntity.ok("일정이 성공적으로 생성되었습니다");
        }
        return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다");
    }
    // 담당 유저 배치
    @PutMapping("/{id}/assign-user")
    public ResponseEntity<String> assignUserToSchedule(@PathVariable Long id, @RequestParam Long userId) {
        Optional<Schedule> optionalSchedule = sS.getScheduleById(id);
        Optional<User> optionalUser = sS.getUserById(userId);
        if (optionalSchedule.isPresent() && optionalUser.isPresent()) {
            sS.addAssignedUser(optionalSchedule.get(), optionalUser.get());
            return ResponseEntity.ok("사용자가 일정에 성공적으로 배정되었습니다");
        }
        return ResponseEntity.badRequest().body("일정 또는 사용자를 찾을 수 없습니다");
    }
    // 담당 유저 제거
    @PutMapping("/{id}/remove-user")
    public ResponseEntity<String> removeUserFromSchedule(@PathVariable Long id, @RequestParam Long userId) {
        Optional<Schedule> optionalSchedule = sS.getScheduleById(id);
        Optional<User> optionalUser = sS.getUserById(userId);
        if (optionalSchedule.isPresent() && optionalUser.isPresent()) {
            sS.removeAssignedUser(optionalSchedule.get(), optionalUser.get());
            return ResponseEntity.ok("사용자가 일정에서 성공적으로 제거되었습니다");
        }
        return ResponseEntity.badRequest().body("일정 또는 사용자를 찾을 수 없습니다");
    }
    // 담당 유저 일정 수정
    @PutMapping("/{id}/update-content")
    public ResponseEntity<String> updateScheduleContent(@PathVariable Long id, @RequestBody ScheduleDto scheduleDTO) {
        Optional<Schedule> optionalSchedule = sS.getScheduleById(id);
        if (optionalSchedule.isPresent() && (optionalSchedule.get().getUser().getId().equals(scheduleDTO.getUserId()) ||
                optionalSchedule.get().getAssignedUsers().stream().anyMatch(user -> user.getId().equals(scheduleDTO.getUserId())))) {
            sS.updateScheduleContent(optionalSchedule.get(), scheduleDTO.getTitle(), scheduleDTO.getContent());
            return ResponseEntity.ok("일정 내용이 성공적으로 업데이트되었습니다");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("일정을 수정할 권한이 없습니다");
    }
    // 일정 수정(ID)
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateScheduleStatus(@PathVariable Long id, @RequestParam String status, @RequestParam Long userId) {
        Optional<Schedule> optionalSchedule = sS.getScheduleById(id);
        if (optionalSchedule.isPresent() && (optionalSchedule.get().getUser().getId().equals(userId) ||
                optionalSchedule.get().getAssignedUsers().stream().anyMatch(user -> user.getId().equals(userId)))) {
            sS.updateScheduleStatus(optionalSchedule.get(), status);
            return ResponseEntity.ok("일정 상태가 성공적으로 업데이트되었습니다");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("일정 상태를 변경할 권한이 없습니다");
    }
}