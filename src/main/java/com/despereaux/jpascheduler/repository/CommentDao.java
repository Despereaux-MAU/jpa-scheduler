package com.despereaux.jpascheduler.repository;

import com.despereaux.jpascheduler.entity.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDao {

    @PersistenceContext
    private EntityManager em;
    // 전체 조회 by 스케쥴
    @Transactional
    public List<Comment> findAllByScheduleId(Long scheduleId) {
        String jpql = "SELECT c FROM Comment c WHERE c.schedule.id = :scheduleId";
        return em.createQuery(jpql, Comment.class)
                .setParameter("scheduleId", scheduleId)
                .getResultList();
    }
    // 부분 조회
    @Transactional
    public Optional<Comment> findById(Long id) {
        Comment c = em.find(Comment.class, id);
        return Optional.ofNullable(c);
    }
    // 댓글 저장
    @Transactional
    public void save(Comment c) {
        em.persist(c);
    }
    // 댓글 수정
    @Transactional
    public void update(Comment c) {
        c.setUpdatedAt(LocalDateTime.now());
        em.merge(c);
    }
    // 댓글 삭제
    @Transactional
    public void delete(Long id) {
        Comment c = em.find(Comment.class, id);
        if (c != null) {
            em.remove(c);
        }
    }
}
