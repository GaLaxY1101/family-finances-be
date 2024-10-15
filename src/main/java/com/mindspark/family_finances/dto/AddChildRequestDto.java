package com.mindspark.family_finances.dto;

public record AddChildRequestDto(
        String email,
        String firstName,
        String lastName,
        String password
) {
}
