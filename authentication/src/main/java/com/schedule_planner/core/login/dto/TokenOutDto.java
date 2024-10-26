package com.schedule_planner.core.login.dto;

import com.schedule_planner.log.SensitiveData;

public record TokenOutDto(@SensitiveData String token) {
}
