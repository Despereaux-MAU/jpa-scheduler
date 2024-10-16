package com.despereaux.jpascheduler.service;

import com.despereaux.jpascheduler.entity.Comment;
import com.despereaux.jpascheduler.repository.CommentDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentDao dao;

    public CommentService(CommentDao dao) {
        this.dao = dao;
    }
    // 전체 조회 by 스케쥴
    public List<Comment> findAllBySchedulerId(Long scheduleId) {
        return dao.findAllByScheduleId(scheduleId);
    }
    // 부분 조회
    public Optional<Comment> findById(Long id) {
        return dao.findById(id);
    }
    // 댓글 저장
    public void save(Comment c) {
        dao.save(c);
    }
    // 댓글 수정
    public void update(Long id, Comment c) {
        Optional<Comment> existingComment = findById(id);
        if (existingComment.isPresent()) {
            Comment updatedComment = existingComment.get();
            updatedComment.setContent(c.getContent());
            updatedComment.setUsername(c.getUsername());
            dao.save(updatedComment);
        }
    }
    // 댓글 삭제
    public void delete(Long id) {
        dao.delete(id);
    }
}
