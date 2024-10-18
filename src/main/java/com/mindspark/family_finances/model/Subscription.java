package com.mindspark.family_finances.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;

@Entity
@Table(name="subscriptions")
@Data
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double amount;
    private LocalDate billingDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private periodOfTimeSub periodoftimesub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

}
