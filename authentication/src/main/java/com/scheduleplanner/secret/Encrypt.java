package com.scheduleplanner.secret;

import org.springframework.security.crypto.bcrypt.BCrypt;

public interface Encrypt {
    String hashPassword(String password);

    boolean checkPassword(String password, String hashed);
}
