package com.despereaux.jpascheduler;

import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Test
    @Rollback(value = false)
    public void testSaveAndFindSchedule() {
        Schedule schedule = new Schedule();
        schedule.setUsername("TestUser");
        schedule.setTitle("Test Title");
        schedule.setContent("Test Content");
        schedule.setCreatedAt(LocalDateTime.now());

        scheduleService.saveSchedule(schedule);

        Optional<Schedule> foundSchedule = scheduleService.getScheduleById(schedule.getId());
        assertTrue(foundSchedule.isPresent());
        assertEquals("TestUser", foundSchedule.get().getUsername());
    }

    @Test
    @Rollback(value = false)
    public void testUpdateSchedule() {
        Schedule schedule = new Schedule();
        schedule.setUsername("TestUser");
        schedule.setTitle("Original Title");
        schedule.setContent("Original Content");
        schedule.setCreatedAt(LocalDateTime.now());

        scheduleService.saveSchedule(schedule);

        schedule.setTitle("Updated Title");
        schedule.setContent("Updated Content");
        scheduleService.updateSchedule(schedule);

        Optional<Schedule> updatedSchedule = scheduleService.getScheduleById(schedule.getId());
        assertTrue(updatedSchedule.isPresent());
        assertEquals("Updated Title", updatedSchedule.get().getTitle());
    }

    @Test
    @Rollback(value = false)
    public void testDeleteSchedule() {
        Schedule schedule = new Schedule();
        schedule.setUsername("TestUser");
        schedule.setTitle("Title to be deleted");
        schedule.setContent("Content to be deleted");
        schedule.setCreatedAt(LocalDateTime.now());

        scheduleService.saveSchedule(schedule);
        scheduleService.deleteSchedule(schedule);

        Optional<Schedule> deletedSchedule = scheduleService.getScheduleById(schedule.getId());
        assertFalse(deletedSchedule.isPresent());
    }
}

