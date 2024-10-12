package com.mindspark.family_finances.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_details")
@Data
public class PaymentDetails {

    @Id
    private Long id;


    @MapsId
    @OneToOne
    @JoinColumn(
            name = "id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Payment payment;

    @Column(nullable = false)
    private LocalDateTime lastPayment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    private enum Frequency{
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    }

}
