package com.despereaux.jpascheduler.service;

import com.despereaux.jpascheduler.entity.Comment;
import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.repository.CommentDao;
import com.despereaux.jpascheduler.repository.ScheduleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentDao cDao;

    @Autowired
    private ScheduleDao sDao;
    // 댓글 저장
    public void saveComment(@NonNull Comment comment) {
        cDao.save(comment);
    }
    // 댓글 조회(ID)
    public Optional<Comment> getCommentById(@NonNull Long id) {
        return cDao.findById(id);
    }
    // 전체 조회
    public List<Comment> getAllComments() {
        return cDao.findAll();
    }
    // 댓글 수정
    public void updateComment(@NonNull Comment comment) {
        comment.setUpdatedAt(LocalDateTime.now());
        cDao.update(comment);
    }
    // 댓글 삭제
    public void deleteComment(@NonNull Comment comment) {
        cDao.delete(comment);
    }
    // 댓글 조회(일정 ID)
    public Optional<Schedule> getScheduleById(Long id) {
        return sDao.findById(id);
    }
}