package com.mindspark.family_finances.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GoalRequest {
    private String name;
    private String description;
    private Double targetAmount;
}
