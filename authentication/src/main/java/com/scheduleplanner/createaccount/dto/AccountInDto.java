package com.scheduleplanner.createaccount.dto;

public record AccountInDto(String username,
                           String email,
                           String password,
                           String passwordConfirmation) {
}
