package com.scheduleplanner.authorization.createaccount.dto;

public record AccountInDto(String username,
                           String email,
                           String password,
                           String passwordConfirmation) {
}
