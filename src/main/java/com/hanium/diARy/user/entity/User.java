package com.hanium.diARy.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(name = "created_at", nullable = false, length = 50)
    private Date createdAt;

    private String image;

}
