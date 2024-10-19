package com.mindspark.family_finances.dto.bankaccount;

import java.time.LocalDate;

public record CreateBankAccountResponseDto (
    Long id,
    double availableBalance,
    double totalBalance,
    LocalDate createdAt
) {
}