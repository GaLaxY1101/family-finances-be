package com.mindspark.family_finances.mapper;

import com.mindspark.family_finances.dto.CreateRegularPaymentDto;
import com.mindspark.family_finances.exception.payment.ReceiverNotFoundException;
import com.mindspark.family_finances.model.BankAccount;
import com.mindspark.family_finances.model.Card;
import com.mindspark.family_finances.model.Payment;
import com.mindspark.family_finances.model.PaymentDetails;
import com.mindspark.family_finances.model.Task;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface PaymentMapper {
    Payment toPayment(CreateRegularPaymentDto paymentDto);

    PaymentDetails toPaymentDetails(CreateRegularPaymentDto paymentDto);

    @AfterMapping
    default void setReceiver(@MappingTarget Payment payment, CreateRegularPaymentDto paymentDto) {
        if (paymentDto.receiverBandAccountId() != null) {
            BankAccount newBA = new BankAccount();
            newBA.setId(paymentDto.receiverBandAccountId());
            payment.setReceiverBankAccount(newBA);
        } else if (paymentDto.receiverCardId() != null) {
            Card card = new Card();
            card.setId(paymentDto.receiverCardId());
            payment.setReceiverCard(card);
        } else {
            throw new ReceiverNotFoundException("Can't find receiver.");
        }
    }
}
