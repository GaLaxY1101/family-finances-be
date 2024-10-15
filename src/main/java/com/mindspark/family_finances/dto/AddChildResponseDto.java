package com.mindspark.family_finances.dto;

public record AddChildResponseDto(
        Long cardId,
        Long userId,
        String email,
        String firstName,
        String lastName
) {
}
