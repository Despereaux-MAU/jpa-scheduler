package com.despereaux.jpascheduler.service;

import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.repository.ScheduleDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleDao dao;

    public ScheduleService(ScheduleDao dao, ScheduleDao scheduleDao) {
        this.dao = dao;
    }
    // 전체 조회
    public List<Schedule> findAll() {
        return dao.findAll();
    }
    // 부분 조회
    public Optional<Schedule> findById(Long id) {
        return dao.findById(id);
    }
    // 일정 저장
    @Transactional
    public void save(Schedule schedule) {
        dao.save(schedule);
    }
    // 일정 수정
    public void update(Long id, Schedule newSchedule) {
        Optional<Schedule> existingSchedule = dao.findById(id);
        if (existingSchedule.isPresent()) {
            Schedule updateSchedule = existingSchedule.get();
            updateSchedule.setTitle(newSchedule.getTitle());
            updateSchedule.setContent(newSchedule.getContent());
            updateSchedule.setUsername(newSchedule.getUsername());
            dao.update(updateSchedule);
        }
    }
    // 일정 삭제
    public void delete(Long id) {
        dao.delete(id);
    }
}
