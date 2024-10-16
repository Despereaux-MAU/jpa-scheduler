package com.despereaux.jpascheduler.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleDto {

    private long id;
    private String username;
    private String title;
    private String content;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
