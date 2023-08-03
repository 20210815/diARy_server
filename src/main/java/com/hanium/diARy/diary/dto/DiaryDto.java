package com.hanium.diARy.diary.dto;


import com.hanium.diARy.user.entity.User;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class DiaryDto {
    private String title;
    private String content;
    private int satisfaction;
    private String tag;
    private boolean isPublic;
    private Date travelPeriod;
    private User user;

    public DiaryDto(String title, String content, String tag, int satisfaction, Date travelPeriod, User user) {
    }

    public DiaryDto(String title, String content, int satisfaction, String tag, boolean isPublic, Date travelPeriod, User user) {this.title = title;
        this.content = content;
        this.satisfaction = satisfaction;
        this.tag = tag;
        this.isPublic = isPublic;
        this.travelPeriod = travelPeriod;
        this.user = user;
    }
}
