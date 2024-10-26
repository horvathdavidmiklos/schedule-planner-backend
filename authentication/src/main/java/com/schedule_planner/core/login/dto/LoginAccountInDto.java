package com.schedule_planner.core.login.dto;

import com.schedule_planner.log.SensitiveData;

public record LoginAccountInDto(
        @SensitiveData String id,  //email or username
        @SensitiveData String password) {
}
