package com.schedule_planner.encrypt;

import com.schedule_planner.exception.baseexception.handled.EncryptException;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class EncryptImpl implements Encrypt {
    public  String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hashed) {
        try {
            return BCrypt.checkpw(password, hashed);
        }catch (IllegalArgumentException e) {
            throw new EncryptException();
        }
    }
}
