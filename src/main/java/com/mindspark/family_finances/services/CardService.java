package com.mindspark.family_finances.services;

import com.mindspark.family_finances.dto.AddChildRequestDto;
import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.Card;
import com.mindspark.family_finances.model.User;
import com.mindspark.family_finances.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public Card createDefaultCard(BankAccount bankAccount) {
        Card card = Card.builder()
                .bankAccount(bankAccount)
                .createdAt(LocalDate.now())
                .expirationDate(LocalDate.now().plusYears(1))
                .balance(0.0)
                .build();
        return cardRepository.save(card);
    }

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
}
