package com.hanium.diARy.plan.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.sql.Date;

@Data
public class LocationDto {
    private Long locationId;
    private Date date;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date time;
    private String name;
    private String address;
}