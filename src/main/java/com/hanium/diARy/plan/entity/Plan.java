package com.hanium.diARy.plan.entity;

import com.hanium.diARy.user.entity.User;
import lombok.Data;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="plan_id")
    private int planId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    private String title;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "public")
    private boolean isPublic;

}
