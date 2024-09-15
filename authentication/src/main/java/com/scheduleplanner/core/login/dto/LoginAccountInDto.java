package com.scheduleplanner.core.login.dto;

public record LoginAccountInDto(
        String id,  //email or username
        String password) {
}
