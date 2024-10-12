package com.mindspark.family_finances.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments_history")
@Data
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_history_seq")
    @SequenceGenerator(name = "payment_history_seq", sequenceName = "payment_history_sequence", allocationSize = 5)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "payment_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_payment_history_payment")
    )
    private Payment payment;

    private enum Status{
        NEW,
        PENDING,
        COMPLETED,
        CANCELLED
    }
}
