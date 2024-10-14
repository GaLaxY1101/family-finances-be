package com.mindspark.family_finances.services;

import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.Card;
import com.mindspark.family_finances.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public Card createDefaultCard(BankAccount bankAccount) {
        Card card = new Card();
        card.setBankAccount(bankAccount);
        card.setCreatedAt(bankAccount.getCreatedAt());
        card.setBalance(0.0);
        card.setExpirationDate(bankAccount.getCreatedAt().plusYears(1));
        return cardRepository.save(card);
    }
}
