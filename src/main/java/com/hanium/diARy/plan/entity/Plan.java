package com.hanium.diARy.plan.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanium.diARy.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long planId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 10)
    private String title;

    @Column(name = "content", length = 10)
    private String content;

    @Column(name = "travel_start", nullable = false)
    private Date travelStart;

    @Column(name = "travel_end", nullable = false)
    private Date travelEnd;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "public", nullable = false)
    private boolean isPublic;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanLike> planLikes;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date(Instant.now().toEpochMilli());
        this.updatedAt = new Date(Instant.now().toEpochMilli());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date(Instant.now().toEpochMilli());
    }
}
