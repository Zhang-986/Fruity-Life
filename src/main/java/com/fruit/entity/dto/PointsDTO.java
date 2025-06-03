package com.fruit.entity.dto;
import lombok.Data;

@Data
public class PointsDTO {
    private String pointsType; // 积分类型，例如 "sign_in" 或 "daily_checkin"
    private Long points; // 积分值，例如 10 或 20
}
