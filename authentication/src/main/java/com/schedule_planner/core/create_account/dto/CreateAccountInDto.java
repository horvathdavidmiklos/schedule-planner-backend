package com.schedule_planner.core.create_account.dto;

import com.schedule_planner.log.SensitiveData;

public record CreateAccountInDto(String username,
                                 @SensitiveData String email,
                                 @SensitiveData String nickname,
                                 @SensitiveData String password,
                                 @SensitiveData String passwordConfirmation) {
}
