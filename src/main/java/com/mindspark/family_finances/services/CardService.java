package com.mindspark.family_finances.services;

import com.mindspark.family_finances.dto.AddChildRequestDto;
import com.mindspark.family_finances.exception.CardNotFoundException;
import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.Card;
import com.mindspark.family_finances.model.User;
import com.mindspark.family_finances.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    @Transactional
    public Card createDefaultCard(BankAccount bankAccount) {
        Card card = Card.builder()
                .bankAccount(bankAccount)
                .createdAt(LocalDate.now())
                .expirationDate(LocalDate.now().plusYears(1))
                .balance(0.0)
                .build();
        return cardRepository.save(card);
    }

    @Transactional
    public Card createChildCard(User child, BankAccount bankAccount) {
        Card card = Card.builder()
                .balance(0.0)
                .createdAt(LocalDate.now())
                .expirationDate(LocalDate.now().plusYears(1))
                .user(child)
                .bankAccount(bankAccount)
                .build();
        return cardRepository.save(card);
    }

    public Card findById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("No such card"));
    }

    public Card findByUserAndBankAccount(User assignee, BankAccount bankAccount) {
        return cardRepository.findByUserIdAndBankAccountId(assignee, bankAccount)
                .orElseThrow(() -> new CardNotFoundException("No such card"));
    }

}
