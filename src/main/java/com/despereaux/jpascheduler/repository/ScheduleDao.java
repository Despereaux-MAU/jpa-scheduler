package com.despereaux.jpascheduler.repository;

import com.despereaux.jpascheduler.entity.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Schedule schedule) {
        entityManager.persist(schedule);
    }

    public Optional<Schedule> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Schedule.class, id));
    }

    public List<Schedule> findAll(Pageable pageable) {
        return entityManager.createQuery("from Schedule order by updatedAt desc", Schedule.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Transactional
    public void update(Schedule schedule) {
        entityManager.merge(schedule);
    }

    @Transactional
    public void delete(Schedule schedule) {
        entityManager.remove(entityManager.contains(schedule) ? schedule : entityManager.merge(schedule));
    }
}
