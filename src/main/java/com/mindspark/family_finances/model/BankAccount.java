package com.mindspark.family_finances.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "bank_accounts")
@EqualsAndHashCode(exclude = {"cards", "users"})
@ToString(exclude = {"cards", "users", "subscriptions"})
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
    private Set<User> users = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Subscription> subscriptions = new HashSet<>();


    public void addUser(User user) {
        users.add(user);
        if (user.getBankAccounts() == null) {
            user.setBankAccounts(Set.of(this));
        } else {
            user.addBankAccount(this);
        }
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getBankAccounts().remove(this);
    }

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
        subscription.setBankAccount(this);
    }

    public void removeSubscription(Subscription subscription) {
        subscriptions.remove(subscription);
        subscription.setBankAccount(null);
    }


    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Goal> goals;


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

