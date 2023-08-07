package com.hanium.diARy.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String email;
    private String password;
    private String image;

    // 기본 생성자, Getter, Setter 등은 필요에 따라 추가할 수 있습니다.
}