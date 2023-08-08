package com.hanium.diARy.diary.dto;


import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDto {
    private String title;
    private int satisfaction;
    private boolean isPublic;
    private Date travelStart;
    private Date travelEnd;
    private User user;
    private String travelDest;
    private String Memo;
    private List<DiaryTagDto> tags;
    private List<DiaryLikeDto> likes;
    private List<CommentDto> comments;

    @Override
    public String toString() {
        return "DiaryDto{" +
                "title='" + title + '\'' +
                ", satisfaction=" + satisfaction +
                ", isPublic=" + isPublic +
                ", travelStart=" + travelStart +
                ", travelEnd=" + travelEnd +
                ", user=" + user +
                ", travelDest='" + travelDest + '\'' +
                ", Memo='" + Memo + '\'' +
                ", tags=" + tags +
                ", likes=" + likes +
                ", comments=" + comments +
                '}';
    }
}

