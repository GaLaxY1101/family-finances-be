package com.mindspark.family_finances.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "bank_accounts")
@Data
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_seq")
    @SequenceGenerator(name = "bank_account_seq", sequenceName = "bank_account_sequence", allocationSize = 5)
    private Long id;

    @Column(nullable = false)
    private Double availableBalance;

    @Column(nullable = false)
    private Double totalBalance;

    @OneToMany(
            mappedBy = "bankAccount"
    )
    private Set<Card> cards;

    @Column(nullable = false)
    private boolean isBlocked;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    private enum Type{
        FAMILY,
        PERSONAL
    }
}
