package com.mindspark.family_finances.services;

import com.mindspark.family_finances.dto.GoalRequest;
import com.mindspark.family_finances.exception.BankAccountDoesNotExistException;
import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.Card;
import com.mindspark.family_finances.model.Goal;
import com.mindspark.family_finances.repository.BankAccountRepository;
import com.mindspark.family_finances.repository.CardRepository;
import com.mindspark.family_finances.repository.GoalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {
    
    private final GoalRepository goalRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CardRepository cardRepository;

    public Goal saveGoal(Long accountId, GoalRequest goalRequest) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountDoesNotExistException("Bank account not found"));
        Goal goal = new Goal();
        goal.setName(goalRequest.getName());
        goal.setDescription(goalRequest.getDescription());
        goal.setTargetAmount(goalRequest.getTargetAmount());
        goal.setBankAccount(bankAccount);

        goalRepository.save(goal);

        bankAccount.addGoal(goal);
        return goal;
    }

    public List<Goal> getAllGoalsByAccountId(Long accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountDoesNotExistException("Bank account not found"));

        return new ArrayList<>(bankAccount.getGoals());
    }

    public void deleteGoal(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found"));
        BankAccount bankAccount = goal.getBankAccount();

        if (goal.getCurrentAmount() > 0) {
            bankAccount.setTotalBalance(bankAccount.getTotalBalance() + goal.getCurrentAmount());
            bankAccountRepository.save(bankAccount);

            if (!bankAccount.getCards().isEmpty()) {
                Card card = bankAccount.getCards().iterator().next();
                card.setBalance(card.getBalance() + goal.getCurrentAmount());
                cardRepository.save(card);
            }
        }

        bankAccount.removeGoal(goal);
        goalRepository.delete(goal);
    }
}
