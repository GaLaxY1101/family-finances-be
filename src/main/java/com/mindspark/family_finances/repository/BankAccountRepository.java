package com.mindspark.family_finances.repository;

import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
