package com.schedule_planner.core.create_account.dto;

public record CreateAccountInDto(String username,
                                 String email,
                                 String nickname,
                                 String password,
                                 String passwordConfirmation) {
}
