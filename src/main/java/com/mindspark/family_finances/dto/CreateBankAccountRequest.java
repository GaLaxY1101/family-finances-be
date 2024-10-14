package com.mindspark.family_finances.dto;

import lombok.Data;

@Data
public class CreateBankAccountRequest {
    private Double availableBalance;
    private Double totalBalance;
    private boolean isBlocked;
}
