package com.hanium.diARy.plan.dto;

import com.hanium.diARy.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanResponseDto {
    private UserDto user;
    private UserDto origin;
    private PlanDto plan;
    private List<PlanLocationDto> locations;
    private List<PlanTagDto> tags;
    private List<PlanLikeDto> likes;
}
