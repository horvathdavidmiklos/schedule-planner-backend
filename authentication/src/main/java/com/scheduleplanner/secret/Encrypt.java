package com.scheduleplanner.secret;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Encrypt {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
