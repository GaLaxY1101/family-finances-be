package com.mindspark.family_finances.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_sequence", allocationSize = 5)
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "sender_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_payments_sender")
    )
    private User sender;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "receiver_bank_account_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_payments_receiver_bank_account")
    )
    private BankAccount receiverBankAccount;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "receiver_card_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_payments_receiver_card")
    )
    private Card receiverCard;

    @Column(nullable = false)
    private boolean isRegular;

    @OneToOne(
            mappedBy = "payment",
            cascade = CascadeType.ALL
    )
    private PaymentDetails paymentDetails;


    @Column(nullable = false)
    private Double amount;
}
