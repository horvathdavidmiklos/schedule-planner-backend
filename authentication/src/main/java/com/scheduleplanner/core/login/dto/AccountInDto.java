package com.scheduleplanner.core.login.dto;

public record AccountInDto(
        String id,  //email or username
        String password) {
}
