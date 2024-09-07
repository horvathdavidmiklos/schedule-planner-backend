package com.scheduleplanner.core.login.dto;

import com.scheduleplanner.common.log.SensitiveData;

public record AccountInDto(
        String id,  //email or username
        String password) {
}
