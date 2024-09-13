package com.scheduleplanner.gateway.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "unverified")
@Setter
@Getter
public class UnverifiedAccount {
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "username",unique = true)
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private String token;
}
