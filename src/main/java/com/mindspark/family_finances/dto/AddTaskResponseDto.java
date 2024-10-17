package com.mindspark.family_finances.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AddTaskResponseDto {

    private Long assigneeId;
    private String name;
    private String description;
    private Double reward;
    private LocalDateTime deadline;

}
