package com.scheduleplanner.secret;

public record AccountInDto(String username,
                           String email,
                           String password,
                           String passwordConfirmation) {
}
