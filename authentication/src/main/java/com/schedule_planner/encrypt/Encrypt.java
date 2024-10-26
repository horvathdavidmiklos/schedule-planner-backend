package com.schedule_planner.encrypt;

public interface Encrypt {
    String hashPassword(String password);

    boolean checkPassword(String password, String hashed);
}
