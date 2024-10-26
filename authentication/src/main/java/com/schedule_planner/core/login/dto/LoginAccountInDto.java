package com.schedule_planner.core.login.dto;

public record LoginAccountInDto(
        String id,  //email or username
        String password) {
}
