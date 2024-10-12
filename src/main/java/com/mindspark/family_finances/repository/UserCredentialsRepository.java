package com.mindspark.family_finances.repository;

import com.mindspark.family_finances.model.User;
import com.mindspark.family_finances.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    Optional<UserCredentials> findByEmail(String email);

}
