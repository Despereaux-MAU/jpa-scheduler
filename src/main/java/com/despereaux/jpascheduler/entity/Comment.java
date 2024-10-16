package com.despereaux.jpascheduler.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String comment;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate createAt;
    private LocalDate updateAt;

    // Constructors
    public Comment() {}

    public Comment(String username, String comment, LocalDate createAt, LocalDate updateAt) {
        this.username = username;
        this.comment = comment;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
    @ManyToOne
    @JoinColumn(name = "schedules_id")
    private Schedule schedule;
}
