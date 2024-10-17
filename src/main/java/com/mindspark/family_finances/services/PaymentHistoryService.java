package com.mindspark.family_finances.services;

import com.mindspark.family_finances.model.Payment;
import com.mindspark.family_finances.model.PaymentHistory;
import com.mindspark.family_finances.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public void save(PaymentHistory paymentHistory) {
        paymentHistoryRepository.save(paymentHistory);
        log.info("{} saved", paymentHistory);
    }

    public PaymentHistory createPaymentHistory(Payment payment) {

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setPayment(payment);
        paymentHistory.setTime(LocalDateTime.now());
        paymentHistory.setStatus(PaymentHistory.Status.NEW);
        save(paymentHistory);
        return paymentHistory;
    }
}
