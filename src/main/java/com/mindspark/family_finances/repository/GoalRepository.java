package com.mindspark.family_finances.repository;

import com.mindspark.family_finances.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    @Query("SELECT MAX(g.id) FROM Goal g WHERE g.bankAccount.id = :bankAccountId")
    Long findMaxIdByBankAccountId(@Param("bankAccountId") Long bankAccountId);
}
