package com.mindspark.family_finances.mapper;

import com.mindspark.family_finances.dto.CreateRegularPaymentDto;
import com.mindspark.family_finances.model.Payment;
import com.mindspark.family_finances.model.PaymentDetails;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface PaymentMapper {
    Payment toPayment(CreateRegularPaymentDto paymentDto);

    PaymentDetails toPaymentDetails(CreateRegularPaymentDto paymentDto);
}
