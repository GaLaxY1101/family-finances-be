package com.mindspark.family_finances.dto;

import java.time.LocalDate;

public record CreateBankAccountResponseDto (
    Long id,
    double availableBalance,
    double totalBalance,
    LocalDate createdAt
) {
}