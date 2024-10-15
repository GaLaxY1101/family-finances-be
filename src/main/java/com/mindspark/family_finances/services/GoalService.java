package com.mindspark.family_finances.services;

import com.mindspark.family_finances.dto.GoalRequest;
import com.mindspark.family_finances.model.Goal;
import com.mindspark.family_finances.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalService transactionService;
    private final GoalRepository goalRepository;


    public Goal saveGoal(GoalRequest goalRequest) {
        Goal goal = new Goal();
        goal.setName(goalRequest.getName());
        goal.setDescription(goalRequest.getDescription());
        goal.setTargetAmount(goalRequest.getTargetAmount());
        return goalRepository.save(goal);
    }

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Goal getGoalById(Long id) {
        return goalRepository.findById(id).orElse(null);
    }
}
