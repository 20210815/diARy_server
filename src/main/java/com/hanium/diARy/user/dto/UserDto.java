package com.hanium.diARy.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String username;
    private String email;
    private String password;
    private String image;
    private Date createdAt;

    public UserDto(String username, String email, String password, String image, Date createdAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.image = image;
        this.createdAt = createdAt;
    }

    // 기본 생성자, Getter, Setter 등은 필요에 따라 추가할 수 있습니다.
}