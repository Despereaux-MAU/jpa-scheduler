package com.despereaux.jpascheduler;

import com.despereaux.jpascheduler.entity.Comment;
import com.despereaux.jpascheduler.entity.Schedule;
import com.despereaux.jpascheduler.service.CommentService;
import com.despereaux.jpascheduler.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ScheduleService scheduleService;

    @Test
    @Rollback(value = false)
    public void testSaveAndFindComment() {
        Schedule schedule = new Schedule();
        schedule.setUsername("TestUser");
        schedule.setTitle("Test Title");
        schedule.setContent("Test Content");
        schedule.setCreatedAt(LocalDateTime.now());
        scheduleService.saveSchedule(schedule);

        Comment comment = new Comment();
        comment.setUsername("CommentUser");
        comment.setContent("Test Comment");
        comment.setCreatedAt(LocalDateTime.now());
        comment.setSchedule(schedule);

        commentService.saveComment(comment);

        Optional<Comment> foundComment = commentService.getCommentById(comment.getId());
        assertTrue(foundComment.isPresent());
        assertEquals("Test Comment", foundComment.get().getContent());
    }

    @Test
    @Rollback(value = false)
    public void testUpdateComment() {
        Schedule schedule = new Schedule();
        schedule.setUsername("TestUser");
        schedule.setTitle("Test Title");
        schedule.setContent("Test Content");
        schedule.setCreatedAt(LocalDateTime.now());
        scheduleService.saveSchedule(schedule);

        Comment comment = new Comment();
        comment.setUsername("CommentUser");
        comment.setContent("Original Comment");
        comment.setCreatedAt(LocalDateTime.now());
        comment.setSchedule(schedule);
        commentService.saveComment(comment);

        comment.setContent("Updated Comment");
        commentService.updateComment(comment);

        Optional<Comment> updatedComment = commentService.getCommentById(comment.getId());
        assertTrue(updatedComment.isPresent());
        assertEquals("Updated Comment", updatedComment.get().getContent());
    }

    @Test
    @Rollback(value = false)
    public void testDeleteComment() {
        Schedule schedule = new Schedule();
        schedule.setUsername("TestUser");
        schedule.setTitle("Test Title");
        schedule.setContent("Test Content");
        schedule.setCreatedAt(LocalDateTime.now());
        scheduleService.saveSchedule(schedule);

        Comment comment = new Comment();
        comment.setUsername("CommentUser");
        comment.setContent("Test Comment");
        comment.setCreatedAt(LocalDateTime.now());
        comment.setSchedule(schedule);
        commentService.saveComment(comment);

        commentService.deleteComment(comment);

        Optional<Comment> deletedComment = commentService.getCommentById(comment.getId());
        assertFalse(deletedComment.isPresent());
    }
}
