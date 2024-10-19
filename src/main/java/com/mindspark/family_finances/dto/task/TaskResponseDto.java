package com.mindspark.family_finances.dto.task;

import com.mindspark.family_finances.model.Task;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDto {

    private String name;
    private String assignerFullName;
    private String assigneeFullName;
    private Long reward;
    private LocalDateTime deadline;
    private Task.Status status;

}
