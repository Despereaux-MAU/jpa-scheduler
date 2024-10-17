package com.despereaux.jpascheduler.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDto {
    private Long userId;
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private long commentCount;
}