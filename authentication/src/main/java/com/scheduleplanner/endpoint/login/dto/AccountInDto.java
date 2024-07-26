package com.scheduleplanner.endpoint.login.dto;

public record AccountInDto(
        String id,  //email or username
        String password) {
}
