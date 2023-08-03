package com.hanium.diARy.diary.dto;


import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.user.entity.User;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class DiaryDto {
    private String title;
    private String content;
    private int satisfaction;
    private boolean isPublic;
    private Date travelStart;
    private Date travelEnd;
    private User user;
    private List<DiaryTag> tags;

    public DiaryDto() {
    }

    public DiaryDto(String title, String content, int satisfaction, boolean isPublic, Date travelStart, Date travelEnd, User user, List<DiaryTag> tags) {
        this.title = title;
        this.content = content;
        this.satisfaction = satisfaction;
        this.isPublic = isPublic;
        this.travelStart = travelStart;
        this.travelEnd = travelEnd;
        this.user = user;
        this.tags = tags;
    }
}