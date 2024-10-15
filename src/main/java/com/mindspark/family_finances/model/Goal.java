package com.mindspark.family_finances.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double targetAmount;
    private Double currentAmount = 0.0;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;
}
