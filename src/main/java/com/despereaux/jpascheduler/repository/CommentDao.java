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
    private EntityManager entityManager;

    @Transactional
    public void save(Comment comment) {
        entityManager.persist(comment);
    }

    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    public List<Comment> findAll() {
        return entityManager.createQuery("from Comment", Comment.class).getResultList();
    }

    @Transactional
    public void update(Comment comment) {
        comment.setUpdatedAt(LocalDateTime.now());
        entityManager.merge(comment);
    }

    @Transactional
    public void delete(Comment comment) {
        entityManager.remove(entityManager.contains(comment) ? comment : entityManager.merge(comment));
    }
}