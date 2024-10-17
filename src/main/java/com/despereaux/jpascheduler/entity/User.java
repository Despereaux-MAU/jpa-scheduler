package com.despereaux.jpascheduler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Version
    private Integer version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // 일정과 연결
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Schedule> schedules = new ArrayList<>();
    // 담당 사용자 배치
    @ManyToMany(mappedBy = "assignedUsers", fetch = FetchType.LAZY)
    private List<Schedule> assignedSchedules = new ArrayList<>();
}
