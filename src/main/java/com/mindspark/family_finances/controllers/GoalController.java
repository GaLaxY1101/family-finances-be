package com.mindspark.family_finances.controllers;

import com.mindspark.family_finances.dto.GoalRequest;
import com.mindspark.family_finances.model.Goal;
import com.mindspark.family_finances.services.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goals")
@RequiredArgsConstructor
@Tag(name = "Goal controller",
        description = "Here we have endpoints for goals management")
public class GoalController {
    private final GoalService goalService;

    @PostMapping("/{accountId}/create")
    @PreAuthorize("hasAuthority('PARENT')")
    @Operation(summary = "Create goal",
            description = "Endpoint for creating goal")
    public ResponseEntity<Goal> createGoal(
            @PathVariable Long accountId,
            @RequestBody GoalRequest goal) {
        Goal savedGoal = goalService.saveGoal(accountId, goal);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedGoal);
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("hasAuthority('PARENT')")
    @Operation(summary = "Get all goals",
            description = "Get all bank account`s goals")
    public List<Goal> getAllGoalsByAccountId(@PathVariable Long accountId) {
        return goalService.getAllGoalsByAccountId(accountId);
    }

    @DeleteMapping("/delete/{goalId}")
    @PreAuthorize("hasAuthority('PARENT')")
    @Operation(summary = "Delete goal",
            description = "Delete goal by id")
    public ResponseEntity<String> deleteGoal(@PathVariable Long goalId){
        goalService.deleteGoal(goalId);
        return ResponseEntity
                .status(200)
                .body("Goal deleted and funds returned (if any)");
    }
}
