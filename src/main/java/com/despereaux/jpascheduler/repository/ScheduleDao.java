package com.despereaux.jpascheduler.repository;

import com.despereaux.jpascheduler.entity.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleDao {

    @PersistenceContext
    private EntityManager em;
    // 일정 저장
    @Transactional
    public void save(Schedule schedule) {
        em.persist(schedule);
    }
    // 일정 조회(ID)
    public Optional<Schedule> findById(Long id) {
        return Optional.ofNullable(em.find(Schedule.class, id));
    }
    // 전체 조회
    public List<Schedule> findAll(Pageable pageable) {
        return em.createQuery("from Schedule order by updatedAt desc", Schedule.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
    // 일정 수정
    @Transactional
    public void update(Schedule schedule) {
        schedule.setUpdatedAt(LocalDateTime.now());
        em.merge(schedule);
    }
    // 일정 삭제
    @Transactional
    public void delete(Schedule schedule) {
        em.remove(em.contains(schedule) ? schedule : em.merge(schedule));
    }
}
