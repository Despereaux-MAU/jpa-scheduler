package com.despereaux.jpascheduler.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDto {
    private Long userId;
    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long commentCount;
}