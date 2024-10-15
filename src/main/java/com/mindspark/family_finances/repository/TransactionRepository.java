package com.mindspark.family_finances.repository;


import com.mindspark.family_finances.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
