package com.despereaux.jpascheduler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Schedule {
    @Version
    private Integer version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;  // 일정 상태 (예: "진행 중", "완료", "취소")
    // 댓글과 연결
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    // 사용자와 연결 및 담당 사용자 배치
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_users",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> assignedUsers = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setSchedule(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setSchedule(null);
    }
}