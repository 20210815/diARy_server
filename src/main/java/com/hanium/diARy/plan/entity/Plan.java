package com.hanium.diARy.plan.entity;

import com.hanium.diARy.user.entity.User;
import lombok.Data;

import jakarta.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "Plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long planId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 10)
    private String title;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "public", nullable = false)
    private boolean isPublic;

    // Getter and Setter, Constructors, etc.
}
