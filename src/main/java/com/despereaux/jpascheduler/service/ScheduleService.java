package com.despereaux.jpascheduler.service;

import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.entity.User;
import com.despereaux.jpascheduler.repository.ScheduleDao;
import com.despereaux.jpascheduler.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired(required = false)
    private ScheduleDao scheduleDao;

    @Autowired
    private UserDao userDao;

    public void saveSchedule(@NonNull Schedule schedule) {
        scheduleDao.save(schedule);
    }

    public Optional<Schedule> getScheduleById(@NonNull Long id) {
        return scheduleDao.findById(id);
    }

    public List<Schedule> getSchedules(Pageable pageable) {
        return scheduleDao.findAll(pageable);
    }

    public void updateSchedule(@NonNull Schedule schedule) {
        schedule.setUpdatedAt(LocalDateTime.now());
        scheduleDao.update(schedule);
    }

    public void deleteSchedule(@NonNull Schedule schedule) {
        scheduleDao.delete(schedule);
    }

    public Optional<User> getUserById(@NonNull Long userId) {
        return userDao.findById(userId);
    }
}