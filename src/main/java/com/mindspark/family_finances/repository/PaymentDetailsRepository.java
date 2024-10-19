package com.mindspark.family_finances.repository;

import com.mindspark.family_finances.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {

}
