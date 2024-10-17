package com.mindspark.family_finances.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@Table(name = "cards")
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_seq")
    @SequenceGenerator(name = "card_seq", sequenceName = "card_sequence", allocationSize = 5)
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "bank_account_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_cards_bankAccount")
    )
    @JsonIgnore
    private BankAccount bankAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_cards_user")
    )
    private User user;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private Double balance;
}
