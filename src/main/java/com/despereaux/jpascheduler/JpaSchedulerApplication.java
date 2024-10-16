package com.despereaux.jpascheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpaSchedulerApplication {

    @Autowired
    ScheduleRepository scheduleRepository;
    CommentRepository commentRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaSchedulerApplication.class, args);
    }

}
