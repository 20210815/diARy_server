package com.hanium.diARy.diary.dto;


//import com.hanium.diARy.plan.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryLocationDto {
    private Long diaryId;
    private String content;
    private String name;
    private String address;

    @Override
    public String toString() {
        return "DiaryLocationDto{" +
                "diaryId=" + diaryId +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
