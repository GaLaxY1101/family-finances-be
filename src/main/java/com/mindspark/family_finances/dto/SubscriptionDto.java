package com.mindspark.family_finances.dto;

import com.mindspark.family_finances.model.periodOfTimeSub;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SubscriptionDto {
    private String name;
    private Double amount;
    private periodOfTimeSub periodOfTimeSub;
}
