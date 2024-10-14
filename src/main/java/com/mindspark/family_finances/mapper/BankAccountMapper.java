package com.mindspark.family_finances.mapper;

import com.mindspark.family_finances.dto.CreateBankAccountResponseDto;
import com.mindspark.family_finances.model.BankAccount;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface BankAccountMapper {
    CreateBankAccountResponseDto toDto(BankAccount bankAccount);
}