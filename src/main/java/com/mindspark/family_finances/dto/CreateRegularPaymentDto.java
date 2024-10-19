package com.mindspark.family_finances.dto;

import com.mindspark.family_finances.model.PaymentDetails;
import java.time.LocalDateTime;

public record CreateRegularPaymentDto(
        double amount,
        int receiverCardId,
        int receiverBandAccountId,
        PaymentDetails.Frequency frequency,
        LocalDateTime firstTimeOfPayment
) {
}
