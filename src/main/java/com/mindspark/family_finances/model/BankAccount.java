package com.mindspark.family_finances.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
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
    private Double totalBalance = 0.0;

    @OneToMany(mappedBy = "bankAccount")
    private Set<Card> cards;

    @Column(nullable = false)
    private boolean isBlocked;

    @Column(nullable = false)
    private LocalDate createdAt;

    @ManyToMany(mappedBy = "bankAccounts")
    @JsonManagedReference
    private Set<User> users = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Type type;


    public void addUser(User user) {
        users.add(user);
        user.getBankAccounts().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getBankAccounts().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id);
    }

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Goal> goals;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", availableBalance=" + availableBalance +
                ", totalBalance=" + totalBalance +
                '}';
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
        goal.setBankAccount(this);
    }

    public void removeGoal(Goal goal) {
        goals.remove(goal);
        goal.setBankAccount(null);
    }

    public enum Type{
        FAMILY,
        PERSONAL
    }
}

