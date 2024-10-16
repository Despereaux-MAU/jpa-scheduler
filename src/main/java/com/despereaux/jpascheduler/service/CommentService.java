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
    private CommentDao commentDao;

    @Autowired
    private ScheduleDao scheduleDao;

    public void saveComment(@NonNull Comment comment) {
        commentDao.save(comment);
    }

    public Optional<Comment> getCommentById(@NonNull Long id) {
        return commentDao.findById(id);
    }

    public List<Comment> getAllComments() {
        return commentDao.findAll();
    }

    public void updateComment(@NonNull Comment comment) {
        comment.setUpdatedAt(LocalDateTime.now());
        commentDao.update(comment);
    }

    public void deleteComment(@NonNull Comment comment) {
        commentDao.delete(comment);
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleDao.findById(id);
    }
}
