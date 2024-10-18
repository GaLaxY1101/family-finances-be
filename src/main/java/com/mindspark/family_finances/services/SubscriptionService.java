package com.mindspark.family_finances.services;

import com.mindspark.family_finances.dto.SubscriptionDto;
import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.Subscription;
import com.mindspark.family_finances.repository.BankAccountRepository;
import com.mindspark.family_finances.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public SubscriptionDto createSubscription(BankAccount bankAccountId, SubscriptionDto subscriptionDTO) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Bank account not found"));

        Subscription subscription = new Subscription();
        subscription.setName(subscriptionDTO.getName());
        subscription.setAmount(subscriptionDTO.getAmount());
        subscription.setPeriodoftimesub(subscriptionDTO.getPeriodOfTimeSub());
        subscription.setBankAccount(bankAccount);

        subscriptionRepository.save(subscription);
        return subscriptionDTO;
    }
}