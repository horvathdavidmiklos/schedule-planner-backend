package com.schedule_planner.core.login.dto;

import com.schedule_planner.log.SensitiveData;

public record ThirdPartyApplicationDataDto(@SensitiveData String nickname,
                                           String username,
                                           @SensitiveData String email,
                                           @SensitiveData String profilePictureUrl) {
}
