package com.mindspark.family_finances.repository;

import com.mindspark.family_finances.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository <Card, Long> {
}
