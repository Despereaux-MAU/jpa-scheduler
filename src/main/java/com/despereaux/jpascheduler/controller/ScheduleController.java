package com.despereaux.jpascheduler.controller;

import com.despereaux.jpascheduler.dto.ScheduleDto;
import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public String createSchedule(@RequestBody ScheduleDto scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setUsername(scheduleDTO.getUsername());
        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setContent(scheduleDTO.getContent());
        schedule.setCreatedAt(LocalDateTime.now());
        scheduleService.saveSchedule(schedule);
        return "일정이 저장되었습니다.";
    }

    @GetMapping("/{id}")
    public ScheduleDto getSchedule(@PathVariable Long id) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        if (schedule.isPresent()) {
            ScheduleDto scheduleDTO = new ScheduleDto();
            scheduleDTO.setId(schedule.get().getId());
            scheduleDTO.setUsername(schedule.get().getUsername());
            scheduleDTO.setTitle(schedule.get().getTitle());
            scheduleDTO.setContent(schedule.get().getContent());
            scheduleDTO.setCreatedAt(schedule.get().getCreatedAt());
            scheduleDTO.setUpdatedAt(schedule.get().getUpdatedAt());
            scheduleDTO.setCommentCount(schedule.get().getComments().size());
            return scheduleDTO;
        }
        throw new RuntimeException("일정을 찾을 수 없습니다.");
    }

    @GetMapping
    public List<ScheduleDto> getAllSchedules(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Schedule> schedules = scheduleService.getSchedules(pageable);
        List<ScheduleDto> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleDto scheduleDTO = new ScheduleDto();
            scheduleDTO.setId(schedule.getId());
            scheduleDTO.setUsername(schedule.getUsername());
            scheduleDTO.setTitle(schedule.getTitle());
            scheduleDTO.setContent(schedule.getContent());
            scheduleDTO.setCreatedAt(schedule.getCreatedAt());
            scheduleDTO.setUpdatedAt(schedule.getUpdatedAt());
            scheduleDTO.setCommentCount(schedule.getComments().size());
            scheduleDTOs.add(scheduleDTO);
        }
        return scheduleDTOs;
    }

    @PutMapping
    public String updateSchedule(@RequestBody ScheduleDto scheduleDTO) {
        Optional<Schedule> optionalSchedule = scheduleService.getScheduleById(scheduleDTO.getId());
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setTitle(scheduleDTO.getTitle());
            schedule.setContent(scheduleDTO.getContent());
            schedule.setUpdatedAt(LocalDateTime.now());
            scheduleService.updateSchedule(schedule);
            return "일정이 수정되었습니다.";
        }
        throw new RuntimeException("일정을 찾을 수 없습니다.");
    }

    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        if (schedule.isPresent()) {
            scheduleService.deleteSchedule(schedule.get());
            return "일정이 삭제되었습니다.";
        }
        throw new RuntimeException("일정을 찾을 수 없습니다.");
    }
}