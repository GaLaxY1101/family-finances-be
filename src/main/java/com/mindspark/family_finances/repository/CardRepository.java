package com.mindspark.family_finances.repository;

import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.Card;
import com.mindspark.family_finances.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository <Card, Long> {

    @Query("SELECT c FROM Card c " +
            "WHERE c.user = :user " +
            "AND c.bankAccount = :bankAccount"
    )
    Optional<Card> findByUserIdAndBankAccountId(User user, BankAccount bankAccount);
}
