package com.mindspark.family_finances.dto;

import com.mindspark.family_finances.model.PaymentDetails;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

public record CreateRegularPaymentDto(
        @NotNull Double amount,
        Long receiverCardId,
        Long receiverBandAccountId,
        @NotNull PaymentDetails.Frequency frequency,
        @NotNull LocalDateTime firstTimeOfPayment
) {
}
