package com.scheduleplanner.core.createaccount.dto;

import com.scheduleplanner.common.log.SensitiveData;

public record AccountInDto(String username,
                           String email,
                           String password,
                           String passwordConfirmation) {
}
