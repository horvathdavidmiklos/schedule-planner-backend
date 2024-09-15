package com.scheduleplanner.core.createaccount.dto;

public record CreateAccountInDto(String username,
                                 String email,
                                 String password,
                                 String passwordConfirmation) {
}
