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
    // 댓글 저장
    @Transactional
    public void save(Comment comment) {
        em.persist(comment);
    }
    // 댓글 조회(ID)
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }
    // 전체 조회
    public List<Comment> findAll() {
        return em.createQuery("from Comment", Comment.class).getResultList();
    }
    // 댓글 수정
    @Transactional
    public void update(Comment comment) {
        comment.setUpdatedAt(LocalDateTime.now());
        em.merge(comment);
    }
    // 댓글 삭제
    @Transactional
    public void delete(Comment comment) {
        em.remove(em.contains(comment) ? comment : em.merge(comment));
    }
}