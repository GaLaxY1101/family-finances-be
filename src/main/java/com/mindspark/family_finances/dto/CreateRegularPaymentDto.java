package com.mindspark.family_finances.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mindspark.family_finances.model.PaymentDetails;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

public record CreateRegularPaymentDto(
        @NotNull Double amount,
        Long receiverCardId,
        Long receiverBandAccountId,
        @NotNull PaymentDetails.Frequency frequency,
        @NotNull @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")LocalDateTime firstTimeOfPayment
) {
}
