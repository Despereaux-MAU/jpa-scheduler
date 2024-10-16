package com.despereaux.jpascheduler.repository;

import com.despereaux.jpascheduler.entity.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleDao {

    @PersistenceContext
    private EntityManager em;
    // 전체 조회
    @Transactional
    public List<Schedule> findAll() {
        String jpql = "SELECT s FROM Schedule s";
        return em.createQuery(jpql, Schedule.class).getResultList();
    }
    // 부분 조회
    @Transactional
    public Optional<Schedule> findById(Long id) {
        Schedule s = em.find(Schedule.class, id);
        return Optional.ofNullable(s);
    }
    // 일정 저장
    @Transactional
    public void save(Schedule s) {
        em.persist(s);
    }
    // 일정 수정
    @Transactional
    public void update(Schedule s) {
        s.setUpdatedAt(LocalDateTime.now());
        em.merge(s);
    }
    // 일정 삭제
    @Transactional
    public void delete(Long id) {
        Schedule s = em.find(Schedule.class, id);
        if (s != null) {
            em.remove(s);
        }
    }
}
