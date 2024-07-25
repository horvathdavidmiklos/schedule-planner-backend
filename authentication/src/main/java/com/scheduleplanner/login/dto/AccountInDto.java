package com.scheduleplanner.login.dto;

public record AccountInDto(
        String id,  //email or username
        String password) {
}
