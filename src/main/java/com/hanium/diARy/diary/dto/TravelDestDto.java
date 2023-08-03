package com.hanium.diARy.diary.dto;


//import com.hanium.diARy.plan.dto.LocationDto;
import com.hanium.diARy.plan.dto.PlanDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelDestDto {
    private Long travelDestId;
    private DiaryDto diary;
    private String content;
    private String name;
    private String address;
    //private LocationDto location;
    private PlanDto plan;
}