package com.despereaux.jpascheduler.repository;

import com.despereaux.jpascheduler.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager em;
    // 사용자 저장
    @Transactional
    public void save(User user) {
        em.persist(user);
    }
    // 사용자 조회(id)
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }
    // 전체 조회
    public List<User> findAll() {
        return em.createQuery("from User", User.class).getResultList();
    }
    // 사용자 수정
    @Transactional
    public void update(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        em.merge(user);
    }
    // 사용자 삭제
    @Transactional
    public void delete(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }
}