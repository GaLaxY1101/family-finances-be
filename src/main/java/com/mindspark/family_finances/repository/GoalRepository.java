package com.mindspark.family_finances.repository;

import com.mindspark.family_finances.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
