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
    private ScheduleDao sDao;

    @Autowired
    private UserDao uDao;
    // 일정 저장
    public void saveSchedule(@NonNull Schedule schedule) {
        sDao.save(schedule);
    }
    // 일정 조회(ID)
    public Optional<Schedule> getScheduleById(@NonNull Long id) {
        return sDao.findById(id);
    }
    // 전체 조회
    public List<Schedule> getSchedules(Pageable pageable) {
        return sDao.findAll(pageable);
    }
    // 일정 수정
    public void updateSchedule(@NonNull Schedule schedule) {
        schedule.setUpdatedAt(LocalDateTime.now());
        sDao.update(schedule);
    }
    // 일정 삭제
    public void deleteSchedule(@NonNull Schedule schedule) {
        sDao.delete(schedule);
    }
    //일정 조회(USER ID)
    public Optional<User> getUserById(@NonNull Long userId) {
        return uDao.findById(userId);
    }
    // 담당 유저 생성
    public void addAssignedUser(@NonNull Schedule schedule, @NonNull User user) {
        schedule.getAssignedUsers().add(user);
        sDao.update(schedule);
    }
    // 일정 삭제(담당 유저)
    public void removeAssignedUser(@NonNull Schedule schedule, @NonNull User user) {
        schedule.getAssignedUsers().remove(user);
        sDao.update(schedule);
    }
    // 일정 수정
    public void updateScheduleContent(@NonNull Schedule schedule, String title, String content) {
        schedule.setTitle(title);
        schedule.setContent(content);
        schedule.setUpdatedAt(LocalDateTime.now());
        sDao.update(schedule);
    }
    // 일정 수정(상태)
    public void updateScheduleStatus(@NonNull Schedule schedule, String status) {
        schedule.setStatus(status);
        schedule.setUpdatedAt(LocalDateTime.now());
        sDao.update(schedule);
    }
}